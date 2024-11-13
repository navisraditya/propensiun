package propensi.propensiun.abuya.controller;

import java.util.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.service.StoreService;
import propensi.propensiun.abuya.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class StoreController {
    @Qualifier("storeServiceImpl")
    @Autowired
    StoreService storeService;

    @Qualifier("userServiceImpl")
    @Autowired
    UserService userService;

    // CREATE STORE
    @GetMapping("/gerai/tambah")
    public String viewAddStoreForm(Model model) {
        StoreModel store = new StoreModel();
        model.addAttribute("store", store);
        return "form-add-store";
    }

    @PostMapping("/gerai/tambah")
    public String viewAddStoreSubmit(@Valid @ModelAttribute("store") StoreModel storeModel,
                                     BindingResult bindingResult,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        if (storeService.existsByStoreName(storeModel.getStoreName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Penambahan gagal. Nama Gerai dan nomor telefon harus unik.");
            model.addAttribute("store", storeModel);
            return "redirect:/gerai/";
        }

//        if (bindingResult.hasErrors()) {
//            // Return to the same page with error messages if there are validation or uniqueness errors
//            redirectAttributes.addFlashAttribute("errorMessage", "Penambahan gagal. Nama Gerai dan nomor telefon harus unik.");
//            model.addAttribute("store", storeModel); // To retain form input values
//            return "redirect:/gerai/";
//        }

        // Extract coordinates from the store address link before adding the store
        storeModel.extractCoordinatesFromLink();
        storeService.addStore(storeModel);
        model.addAttribute("storeName", storeModel.getStoreName());
        return "redirect:/gerai/";
    }

    @GetMapping("/gerai/check-store-name")
    @ResponseBody
    public boolean checkStoreNameExists(@RequestParam String storeName) {
        return storeService.existsByStoreName(storeName);
    }

    @GetMapping("/gerai/check-store-phone")
    @ResponseBody
    public boolean checkPhoneNameExists(@RequestParam String storePhone) {
        return storeService.existsByStorePhone(storePhone);
    }

    // READ STORE
    @GetMapping("/gerai/")
    public String viewStoreList(Model model) {
        List<StoreModel> stores = storeService.getAllStores();
        List<UserModel> listStoreManager = userService.findStoreManagers();
        UserModel loggedInUser = userService.getCurrentAuthenticatedUser();
        model.addAttribute("stores", stores);
        model.addAttribute("listStoreManager", listStoreManager);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("store", new StoreModel());
        return "view-store";
    }

    // UPDATE STORE
    @GetMapping("/gerai/ubah/{idGerai}")
    public String viewUpdateStoreForm(@PathVariable String idGerai, Model model) {
        StoreModel store = storeService.getStoreById(idGerai);
        model.addAttribute("store", store);
        return "form-update-store";
    }

    @PostMapping("/gerai/ubah/{idGerai}")
    public String viewUpdateStoreSubmit(@PathVariable String idGerai, @ModelAttribute StoreModel store, Model model) {
        // Fetch the current store data from the database
        StoreModel existingStore = storeService.getStoreById(idGerai);

        // Update the store details
        existingStore.setStoreName(store.getStoreName());
        existingStore.setStoreAddr(store.getStoreAddr());
        existingStore.setStoreAddrLink(store.getStoreAddrLink());
        existingStore.setStorePhone(store.getStorePhone());
        existingStore.setStoreManager(store.getStoreManager());

        // Extract coordinates from the new store address link
        existingStore.extractCoordinatesFromLink();


        storeService.updateStore(existingStore);
        return "redirect:/gerai/";
    }

    // DELETE STORE
    @PostMapping("/gerai/hapus/{idGerai}")
    public String deleteStore(@PathVariable String idGerai) {
        storeService.deleteStore(idGerai);
        return "redirect:/gerai/";
    }

}
