package propensi.propensiun.abuya.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.repository.StoreDB;

import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreDB storeDB;

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

    @Override
    public List<StoreModel> getAllStoreByIds(List<Integer> listStore) {
        List<StoreModel> selectedStores = storeDB.findAllById(listStore);

        return selectedStores;
    }
}
