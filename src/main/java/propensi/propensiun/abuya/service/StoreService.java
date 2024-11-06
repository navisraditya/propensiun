package propensi.propensiun.abuya.service;

import java.util.List;

import propensi.propensiun.abuya.model.StoreModel;

public interface StoreService {
    void addStore(StoreModel store);

    public StoreModel getStoreById(String id);

    void updateStore(StoreModel store);

    void deleteStore(String id);

    List<StoreModel> findAll();
}
