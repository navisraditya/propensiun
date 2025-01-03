package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import propensi.propensiun.abuya.model.MenuModel;
import propensi.propensiun.abuya.repository.MenuDb;

import java.util.List;
import java.util.UUID;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDb menuDb;

    @Autowired
    private SupabaseService supabaseService;

    public List<MenuModel> getAllMenus() {
        return menuDb.findAll();
    }

    @Override
    public List<MenuModel> getMenusByCategory(MenuModel.Kategori kategori) {
        return menuDb.findByKategori(kategori);
    }


    @Override
    public MenuModel addMenu(MenuModel menu, MultipartFile image) throws Exception {
        if (menuDb.existsByNamaIgnoreCase(menu.getNama())) {
            throw new IllegalArgumentException("Nama menu sudah ada, silakan gunakan nama yang lain.");
        }

        if (menu.getNama().length() < 5) {
            throw new IllegalArgumentException("Nama menu harus memiliki minimal 5 karakter.");
        }

        if (menu.getDeskripsi() == null || menu.getDeskripsi().isEmpty()) {
            throw new IllegalArgumentException("Deskripsi tidak boleh kosong.");
        }

        // Upload gambar menu ke Supabase dan ambil URL-nya
        String imageUrl = supabaseService.uploadImage(image);
        menu.setGambar(imageUrl);

        return menuDb.save(menu);
    }

    @Override
    public MenuModel getMenuById(UUID uuid) {
        return menuDb.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Menu tidak ditemukan"));
    }

    @Override
    public MenuModel updateMenu(MenuModel menu) {
        return null;
    }

    @Override
    public MenuModel updateMenu(MenuModel menu, MultipartFile image) throws Exception {

        MenuModel existingMenu = menuDb.findById(menu.getUuid()).orElseThrow(() -> new IllegalArgumentException("Menu tidak ditemukan"));

        if (!menu.getNama().equalsIgnoreCase(existingMenu.getNama()) && menuDb.existsByNamaIgnoreCase(menu.getNama())) {
            throw new IllegalArgumentException("Nama menu sudah ada, silakan gunakan nama yang lain.");
        }

        if (menu.getNama().length() < 5) {
            throw new IllegalArgumentException("Nama menu harus memiliki minimal 5 karakter.");
        }


        if (menu.getDeskripsi() == null || menu.getDeskripsi().isEmpty()) {
            throw new IllegalArgumentException("Deskripsi tidak boleh kosong.");
        }

        if (image != null && !image.isEmpty()) {
            String imageUrl = supabaseService.uploadImage(image);
            menu.setGambar(imageUrl);
        } else {
            menu.setGambar(existingMenu.getGambar());
        }

        return menuDb.save(menu);
    }

    @Override
    public void deleteMenu(UUID uuid) {
        menuDb.deleteById(uuid);
    }

    @Override
    public String uploadImage(MultipartFile image) throws Exception {
        return supabaseService.uploadImage(image);
    }


}



