package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import propensi.propensiun.abuya.model.MenuModel;
import propensi.propensiun.abuya.repository.MenuDb;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDb menuDb;

    @Autowired
    private SupabaseService supabaseService;

    @Override
    public MenuModel addMenu(MenuModel menu, MultipartFile image) throws Exception {
        // Validasi apakah nama menu sudah ada
        Optional<MenuModel> existingMenu = menuDb.findByNama(menu.getNama());
        if (existingMenu.isPresent()) {
            throw new IllegalArgumentException("Nama menu sudah ada, silakan gunakan nama yang lain.");
        }

        // Upload gambar menu ke Supabase dan ambil URL-nya
        String imageUrl = supabaseService.uploadImage(image);
        menu.setGambar(imageUrl);

        // Simpan menu baru ke database
        return menuDb.save(menu);
    }

}



