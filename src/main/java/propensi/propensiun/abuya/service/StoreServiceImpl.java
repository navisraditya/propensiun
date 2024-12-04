package propensi.propensiun.abuya.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.FeedbackModel;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.repository.FeedbackDb;
import propensi.propensiun.abuya.repository.StoreDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreDB storeDB;

    @Autowired
    FeedbackDb feedbackDB;

    public void addStore(StoreModel store) { storeDB.save(store); }

    public StoreModel getStoreById(String id) { return storeDB.findById(Integer.valueOf(id)).orElse(null); }

    public List<StoreModel> getAllStores() {return storeDB.findAll();}

    public void updateStore(StoreModel store) { storeDB.save(store); }

    public void deleteStore(String id) { storeDB.deleteById(Integer.valueOf(id));}

    @Override
    public boolean existsByStoreName(String storeName) {
        return storeDB.existsByStoreName(storeName);
    }

    @Override
    public boolean existsByStorePhone(String storePhone){
        return storeDB.existsByStorePhone(storePhone);
    }

    public boolean isStoreNameInUse(String storeName, Integer storeUuid) {
        return storeUuid == null
                ? storeDB.existsByStoreName(storeName)
                : storeDB.existsByStoreNameAndUuidNot(storeName, storeUuid);
    }

    public boolean isStorePhoneInUse(String storePhone, Integer storeUuid) {
        return storeUuid == null
                ? storeDB.existsByStorePhone(storePhone)
                : storeDB.existsByStorePhoneAndUuidNot(storePhone, storeUuid);
    }


    @Override
    public List<StoreModel> getAllStoreByIds(List<Integer> listStore) {
        List<StoreModel> selectedStores = storeDB.findAllById(listStore);

        return selectedStores;
    }

    @Transactional
    public void updateStoreAverages(Integer storeId) {
        // Fetch the store
        StoreModel store = storeDB.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        // Retrieve feedbacks for the store
        List<FeedbackModel> feedbacks = feedbackDB.findByStoreUuid(storeId);

        if (feedbacks.isEmpty()) {
            // If no feedbacks, set averages to 0
            store.setAverageMenu(0.0f);
            store.setAveragePelayanan(0.0f);
            store.setAverageFasilitas(0.0f);
        } else {
            // Calculate averages
            float totalMenu = 0.0f;
            float totalPelayanan = 0.0f;
            float totalFasilitas = 0.0f;

            for (FeedbackModel feedback : feedbacks) {
                totalMenu += feedback.getMenu();
                totalPelayanan += feedback.getPelayanan();
                totalFasilitas += feedback.getFasilitas();
            }

            int count = feedbacks.size();
            store.setAverageMenu(totalMenu / count);
            store.setAveragePelayanan(totalPelayanan / count);
            store.setAverageFasilitas(totalFasilitas / count);
        }

        // Save the updated store
        storeDB.save(store);
    }

    @Transactional
    public void updateAllStoreAverages() {
        List<StoreModel> allStores = storeDB.findAll();
        for (StoreModel store : allStores) {
            updateStoreAverages(store.getUuid());
        }
    }

    public Map<String, Float> calculateGlobalAveragesFromFeedback() {
        List<FeedbackModel> feedbacks = feedbackDB.findAll();

        float totalMenu = 0.0f;
        float totalPelayanan = 0.0f;
        float totalFasilitas = 0.0f;
        int totalFeedbacks = feedbacks.size();

        for (FeedbackModel feedback : feedbacks) {
            totalMenu += feedback.getMenu();
            totalPelayanan += feedback.getPelayanan();
            totalFasilitas += feedback.getFasilitas();
        }

        // Avoid division by zero
        float averageMenu = totalFeedbacks > 0 ? totalMenu / totalFeedbacks : 0.0f;
        float averagePelayanan = totalFeedbacks > 0 ? totalPelayanan / totalFeedbacks : 0.0f;
        float averageFasilitas = totalFeedbacks > 0 ? totalFasilitas / totalFeedbacks : 0.0f;

        // Round to 1 decimal place
        averageMenu = Math.round(averageMenu * 10) / 10.0f;
        averagePelayanan = Math.round(averagePelayanan * 10) / 10.0f;
        averageFasilitas = Math.round(averageFasilitas * 10) / 10.0f;

        Map<String, Float> globalAverages = new HashMap<>();
        globalAverages.put("menu", averageMenu);
        globalAverages.put("pelayanan", averagePelayanan);
        globalAverages.put("fasilitas", averageFasilitas);

        return globalAverages;
    }

    @Transactional
    public float calculateStoreOverallAverage(StoreModel store) {
        // Retrieve feedbacks for the given store
        List<FeedbackModel> feedbacks = feedbackDB.findByStoreUuid(store.getUuid());

        if (feedbacks.isEmpty()) {
            return 0.0f; // Return 0 if there are no feedbacks
        }

        // Calculate the total for each aspect
        float totalMenu = 0.0f;
        float totalPelayanan = 0.0f;
        float totalFasilitas = 0.0f;

        for (FeedbackModel feedback : feedbacks) {
            totalMenu += feedback.getMenu();
            totalPelayanan += feedback.getPelayanan();
            totalFasilitas += feedback.getFasilitas();
        }

        // Calculate the combined average for the store
        int feedbackCount = feedbacks.size();
        float overallAverage = (totalMenu + totalPelayanan + totalFasilitas) / (feedbackCount * 3);

        // Format to 1 decimal place
        overallAverage = Math.round(overallAverage * 10) / 10.0f;

        return overallAverage;
    }

    @Transactional
    public void updateAllStoreOverallAverages() {
        List<StoreModel> stores = storeDB.findAll();

        for (StoreModel store : stores) {
            float overallAverage = calculateStoreOverallAverage(store);
            store.setAverageTotal(overallAverage); // Ensure you have a field for this in your model
            storeDB.save(store);
        }
    }
}
