package propensi.propensiun.abuya.service;

import org.springframework.web.multipart.MultipartFile;
import propensi.propensiun.abuya.model.MenuModel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MenuService {

    // Menambahkan menu baru
    MenuModel addMenu(MenuModel menu, MultipartFile image) throws Exception;

    List<MenuModel> getAllMenus();

    MenuModel getMenuById(UUID uuid);

    MenuModel updateMenu(MenuModel menu);

    void deleteMenu(UUID uuid);

    String uploadImage(MultipartFile image) throws Exception;
}


