package propensi.propensiun.abuya.controller;

import org.springframework.security.core.Authentication;

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


    @GetMapping("/menu-list")
    public String showMenuList(Model model, Authentication authentication) {
        List<MenuModel> menus = menuService.getAllMenus();
        model.addAttribute("menus", menus);

        if (authentication != null) {
            String currentRole = authentication.getAuthorities().toString();
            model.addAttribute("currentRole", currentRole);
        }

        return "menu-list";
    }


    @GetMapping("/form-add-menu")
    public String showAddMenuForm(Model model) {
        return "form-add-menu";
    }


    @PostMapping("/addMenu")
    public String addMenu(@RequestParam("nama") String nama,
                          @RequestParam("kategori") MenuModel.Kategori kategori,
                          @RequestParam("deskripsi") String deskripsi,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            MenuModel menu = new MenuModel();
            menu.setNama(nama);
            menu.setKategori(kategori);
            menu.setDeskripsi(deskripsi);

            menuService.addMenu(menu, imageFile);
            redirectAttributes.addFlashAttribute("message", "Menu berhasil ditambahkan!");
            return "redirect:/menu/menu-list";
        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());
            return "form-add-menu";
        } catch (Exception e) {

            model.addAttribute("error", "Terjadi kesalahan saat menambahkan menu: " + e.getMessage());
            return "form-add-menu";
        }
    }



    @PostMapping("/deleteMenu")
    public String deleteMenu(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {

        menuService.deleteMenu(id);
        RedirectAttributes message = redirectAttributes.addFlashAttribute("message", "Menu berhasil dihapus!");

        return "redirect:/menu/menu-list";
    }


    @GetMapping("/form-edit-menu/{id}")
    public String showEditMenuForm(@PathVariable("id") UUID id, Model model) {

        MenuModel menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);

        return "form-edit-menu";
    }


    @PostMapping("/editMenu/{id}")
    public String editMenu(@PathVariable("id") UUID id,
                           @RequestParam("nama") String nama,
                           @RequestParam("kategori") MenuModel.Kategori kategori,
                           @RequestParam("deskripsi") String deskripsi,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) {
        try {
            MenuModel menu = menuService.getMenuById(id);
            menu.setNama(nama);
            menu.setKategori(kategori);
            menu.setDeskripsi(deskripsi);
            MenuModel updatedMenu = menuService.updateMenu(menu);
            redirectAttributes.addFlashAttribute("message", "Menu berhasil diubah!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal mengubah menu: " + e.getMessage());
        }
        return "redirect:/menu/form-edit-menu/{id}";
    }
}