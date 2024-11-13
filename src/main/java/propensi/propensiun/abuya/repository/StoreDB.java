package propensi.propensiun.abuya.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.propensiun.abuya.model.StoreModel;

@Repository
public interface StoreDB extends JpaRepository<StoreModel, Integer> {
    boolean existsByStoreName(String storeName);
    boolean existsByStorePhone(String storePhone);

    // Use 'uuid' instead of 'id' in the methods
    boolean existsByStoreNameAndUuidNot(String storeName, Integer uuid);
    boolean existsByStorePhoneAndUuidNot(String storePhone, Integer uuid);
}

