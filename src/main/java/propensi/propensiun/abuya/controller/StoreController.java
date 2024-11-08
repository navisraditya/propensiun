package propensi.propensiun.abuya.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class StoreController {
    @Qualifier("storeServiceImpl")
    @Autowired
    StoreService storeService;

    // CREATE STORE
    @GetMapping("/gerai/tambah")
    public String viewAddStoreForm(Model model) {
        StoreModel store = new StoreModel();
        model.addAttribute("store", store);
        return "form-add-store";
    }

    @PostMapping("/gerai/tambah")
    public String viewAddStoreSubmit(@ModelAttribute StoreModel storeModel, Model model) {
        // Extract coordinates from the store address link before adding the store
        storeModel.extractCoordinatesFromLink();
        storeService.addStore(storeModel);
        model.addAttribute("storeName", storeModel.getStoreName());
        return "redirect:/gerai/";
    }

    // READ STORE
    @GetMapping("/gerai/")
    public String viewStoreList(Model model) {
        List<StoreModel> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);
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
