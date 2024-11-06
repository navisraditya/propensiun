package propensi.propensiun.abuya.service;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.repository.StoreDB;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreDB storeDB;

    public void addStore(StoreModel store) {
        storeDB.save(store);
    }

    public StoreModel getStoreById(String id) {
        return storeDB.findById(Integer.valueOf(id)).orElse(null);
    }

    public void updateStore(StoreModel store) {
        storeDB.save(store);
    }

    public void deleteStore(String id) {
        storeDB.deleteById(Integer.valueOf(id));
    }

    @Override
    public List<StoreModel> findAll() {
        return storeDB.findAll();
    }
}
