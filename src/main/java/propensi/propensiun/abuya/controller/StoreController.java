package propensi.propensiun.abuya.controller;

import java.util.*;

//Import Models


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import propensi.propensiun.abuya.model.StoreModel;
//import propensi.propensiun.abuya.model.StoreManagerModel;

//Import Libraries
import propensi.propensiun.abuya.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;


@Controller
public class StoreController {
    @Qualifier("storeServiceImpl")
    @Autowired
    StoreService storeService;

    //CREATE STORE
    @GetMapping("/gerai/tambah")
    public String viewAddStoreForm(Model model) {
        StoreModel store = new StoreModel();
        model.addAttribute("store", store);
        return "form-add-store";
    }

    @PostMapping("/gerai/tambah")
    public String viewAddStoreSubmit(@ModelAttribute StoreModel storeModel, Model model) {
        storeService.addStore(storeModel);
        model.addAttribute("storeName", storeModel.getId());
        return "redirect:/gerai/tambah";
    }

    //UPDATE STORE
    @GetMapping("/gerai/ubah/{idGerai}")
    public String  viewUpdateStoreForm(@PathVariable String idGerai, Model model) {
        StoreModel store = storeService.getStoreById(idGerai);
        model.addAttribute("store", store);
        return "form-update-store";
    }

    @PostMapping("/gerai/ubah/{idGerai}")
    public String viewUpdateStoreSubmit(@PathVariable String idGerai, @ModelAttribute StoreModel store, Model model) {
        // Fetch the current store data from the database
        StoreModel existingStore = storeService.getStoreById(idGerai);

        // Update the store details
        storeService.updateStore(store);
        return "redirect:/gerai/tambah";
    }

    //DELETE STORE
    @GetMapping("/gerai/delete")
    public String deleteStore(@RequestParam("id") String id) {
        storeService.deleteStore(id);
        return "redirect:/gerai/tambah";
    }
}
