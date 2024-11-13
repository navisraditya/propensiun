package propensi.propensiun.abuya.service;
import propensi.propensiun.abuya.model.StoreModel;

import java.util.List;

public interface StoreService {
    void addStore(StoreModel store);

    public StoreModel getStoreById(String id);

    List<StoreModel> getAllStores();

    void updateStore(StoreModel store);

    void deleteStore(String id);

    List<StoreModel> getAllStoreByIds(List<Integer> listStore);
}
