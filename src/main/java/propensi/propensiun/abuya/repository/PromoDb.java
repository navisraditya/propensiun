package propensi.propensiun.abuya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import propensi.propensiun.abuya.model.PromoModel;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;
// import propensi.propensiun.abuya.model.PromoModel;

public interface PromoDb extends JpaRepository<PromoModel, Integer> {

    @Query("SELECT promo FROM PromoModel promo WHERE :uuid MEMBER OF promo.storeList")
    List<PromoModel> findPromo(@Param("uuid") Integer uuid);

}
