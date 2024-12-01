package propensi.propensiun.abuya.service;

import org.springframework.web.multipart.MultipartFile;
import propensi.propensiun.abuya.model.MenuModel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MenuService {

    // Menambahkan menu baru
    MenuModel addMenu(MenuModel menu, MultipartFile image) throws Exception;


}


