package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.propensiun.abuya.model.MenuModel;
import propensi.propensiun.abuya.service.MenuService;
import propensi.propensiun.abuya.service.SupabaseServiceImpl;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping("/form-add-menu")
    public String showAddMenuForm(Model model) {
        return "form-add-menu";
    }


    @PostMapping("/addMenu")
    public String addMenu(@RequestParam("nama") String nama,
                          @RequestParam("kategori") MenuModel.Kategori kategori,
                          @RequestParam("deskripsi") String deskripsi,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          RedirectAttributes redirectAttributes) {
        try {

            MenuModel menu = new MenuModel();
            menu.setNama(nama);
            menu.setKategori(kategori);
            menu.setDeskripsi(deskripsi);

            MenuModel savedMenu = menuService.addMenu(menu, imageFile);
            redirectAttributes.addFlashAttribute("message", "Menu berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menambahkan menu: " + e.getMessage());
        }
        return "redirect:/menu/menu-list";
    }



}


