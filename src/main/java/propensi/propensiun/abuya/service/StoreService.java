package propensi.propensiun.abuya.service;
import propensi.propensiun.abuya.model.StoreModel;

import java.util.List;

public interface StoreService {
    void addStore(StoreModel store);

    public StoreModel getStoreById(String id);

    List<StoreModel> getAllStores();

    void updateStore(StoreModel store);

    void deleteStore(String id);

    boolean existsByStoreName(String storeName);
    boolean existsByStorePhone(String storePhone);
    boolean isStoreNameInUse(String storeName, Integer storeUuid);
    boolean isStorePhoneInUse(String storePhone, Integer storeUuid);

    List<StoreModel> getAllStoreByIds(List<Integer> listStore);
}
