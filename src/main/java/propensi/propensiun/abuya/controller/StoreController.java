package propensi.propensiun.abuya.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.model.FeedbackModel;
import propensi.propensiun.abuya.service.StoreService;
import propensi.propensiun.abuya.service.UserService;
import propensi.propensiun.abuya.service.FeedbackService;
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

    @Qualifier("feedbackServiceImpl")
    @Autowired
    FeedbackService feedbackService;

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
        // Update averages for all stores
        storeService.updateAllStoreAverages();
        storeService.updateAllStoreOverallAverages();

        List<StoreModel> stores = storeService.getAllStores();
        List<UserModel> listStoreManager = userService.findStoreManagers();
        UserModel loggedInUser = userService.getCurrentAuthenticatedUser();
        model.addAttribute("stores", stores);
        model.addAttribute("listStoreManager", listStoreManager);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("store", new StoreModel());
        return "view-store";
    }

        @GetMapping("/gerai/detail/{idGerai}")
        public String detailGeraiPage(@PathVariable String idGerai, Model model) {
            StoreModel store = storeService.getStoreById(idGerai);
            if (store == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
            }


            // Update averages for all stores
            storeService.updateAllStoreAverages();
            storeService.updateAllStoreOverallAverages();

            List<StoreModel> stores = storeService.getAllStores();
            List<UserModel> listStoreManager = userService.findStoreManagers();
            List<FeedbackModel> feedbacks = feedbackService.getFeedbacksbyStoreId(Integer.valueOf(idGerai));

            // Sort feedbacks based on tanggal_kunjungan and waktu_pengisian
            feedbacks.sort(Comparator.comparing(FeedbackModel::getTanggalKunjungan)
                    .thenComparing(FeedbackModel::getWaktuPengisian).reversed());

            // Get the top 3 feedbacks
            List<FeedbackModel> topFeedbacks = feedbacks.stream().limit(3).collect(Collectors.toList());

            // Calculate global averages directly from feedback
            Map<String, Float> globalAverages = storeService.calculateGlobalAveragesFromFeedback();

            // Calculate combined average
            float combinedAverage = feedbackService.calculateCombinedAverage();
            String formattedCombinedAverage = String.format("%.1f", combinedAverage);

            // Find the highest and lowest-rated stores
            StoreModel highestRatedStore = stores.stream()
                    .max(Comparator.comparing(StoreModel::getAverageTotal))
                    .orElse(null); // Handle the case if no stores exist

            StoreModel lowestRatedStore = stores.stream()
                    .min(Comparator.comparing(StoreModel::getAverageTotal))
                    .orElse(null);

            // Add the store details to the model
            model.addAttribute("store", store);
            model.addAttribute("listStoreManager", listStoreManager);
            model.addAttribute("feedbacks", topFeedbacks);
            model.addAttribute("jumlahFeedback", feedbacks.size());
            model.addAttribute("globalAverages", globalAverages);
            model.addAttribute("combinedAverage", formattedCombinedAverage);

            return "view-detail-gerai";
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

    @GetMapping("/kelola-gerai")
    public String kelolaGeraiPage(Model model) {
        // Update averages for all stores
        storeService.updateAllStoreAverages();
        storeService.updateAllStoreOverallAverages();

        List<StoreModel> stores = storeService.getAllStores();
        List<FeedbackModel> feedbacks = feedbackService.getAllFeedback();

        // Sort feedbacks based on tanggal_kunjungan and waktu_pengisian
        feedbacks.sort(Comparator.comparing(FeedbackModel::getTanggalKunjungan)
                .thenComparing(FeedbackModel::getWaktuPengisian).reversed());

        // Get the top 3 feedbacks
        List<FeedbackModel> topFeedbacks = feedbacks.stream().limit(3).collect(Collectors.toList());

        // Calculate global averages directly from feedback
        Map<String, Float> globalAverages = storeService.calculateGlobalAveragesFromFeedback();

        // Calculate combined average
        float combinedAverage = feedbackService.calculateCombinedAverage();
        String formattedCombinedAverage = String.format("%.1f", combinedAverage);

        // Find the highest and lowest-rated stores
        StoreModel highestRatedStore = stores.stream()
                .max(Comparator.comparing(StoreModel::getAverageTotal))
                .orElse(null); // Handle the case if no stores exist

        StoreModel lowestRatedStore = stores.stream()
                .min(Comparator.comparing(StoreModel::getAverageTotal))
                .orElse(null);

        model.addAttribute("stores", stores);
        model.addAttribute("feedbacks", topFeedbacks);
        model.addAttribute("jumlahGerai", stores.size());
        model.addAttribute("jumlahFeedback", feedbacks.size());
        model.addAttribute("globalAverages", globalAverages);
        model.addAttribute("combinedAverage", formattedCombinedAverage);
        model.addAttribute("highestRatedStore", highestRatedStore);
        model.addAttribute("lowestRatedStore", lowestRatedStore);

        return "kelola-gerai";
    }

    @GetMapping("/api/feedback-global-average")
    @ResponseBody
    public Map<String, Object> getFeedbackGlobalAverageOverTime(
            @RequestParam(value = "range", required = false, defaultValue = "daily") String range,
            @RequestParam(value = "storeId", required = false) String storeId) {

        // Filter feedbacks by storeId if it's provided
        List<FeedbackModel> feedbacks;
        if (storeId != null) {
            feedbacks = feedbackService.getFeedbacksbyStoreId(Integer.valueOf(storeId)); // Modify to filter by storeId
        } else {
            feedbacks = feedbackService.getAllFeedback(); // Use all feedbacks if no storeId
        }

        // Define date formatter based on the range (daily or monthly)
        DateTimeFormatter formatter = range.equals("monthly")
                ? DateTimeFormatter.ofPattern("MMM-yyyy")
                : DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        // Group feedback data and calculate averages
        Map<String, Double> groupedData = feedbacks.stream()
                .collect(Collectors.groupingBy(
                        feedback -> {
                            if (range.equals("monthly")) {
                                // Normalize to the first day of the month for grouping
                                return feedback.getTanggalKunjungan()
                                        .withDayOfMonth(1)
                                        .format(formatter);
                            }
                            // Group by exact date for daily range
                            return feedback.getTanggalKunjungan().format(formatter);
                        },
                        Collectors.averagingDouble(f ->
                                (f.getPelayanan() + f.getMenu() + f.getFasilitas()) / 3.0
                        )
                ));

        // Sort labels and prepare formatted averages in the same order
        List<String> labels = new ArrayList<>(groupedData.keySet());
        labels.sort(Comparator.comparing(label -> LocalDate.parse(label, formatter))); // Parse labels for correct ordering

        // Format averages to one decimal place
        List<Double> averages = labels.stream()
                .map(label -> Double.valueOf(String.format("%.1f", groupedData.get(label))))
                .collect(Collectors.toList());

        // Prepare response map
        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels); // List<String>
        response.put("averages", averages); // List<Double>

        return response; // Return response with labels and averages
    }

    @GetMapping("/api/store-feedback/{storeId}")
    @ResponseBody
    public Map<String, Object> getStoreFeedback(@PathVariable Integer storeId) {
        // Fetch store feedback data by storeId
        StoreModel store = storeService.getStoreById(String.valueOf(storeId));

        // Calculate the averages for menu, fasilitas, and pelayanan
        double menuAverage = store.getAverageMenu();
        double fasilitasAverage = store.getAverageFasilitas();
        double pelayananAverage = store.getAveragePelayanan();

        // Prepare the data for the frontend
        Map<String, Object> response = new HashMap<>();
        response.put("averages", Map.of(
                "menu", menuAverage,
                "fasilitas", fasilitasAverage,
                "pelayanan", pelayananAverage
        ));

        return response;
    }



}
