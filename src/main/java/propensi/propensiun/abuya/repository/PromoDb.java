package propensi.propensiun.abuya.repository;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import propensi.propensiun.abuya.model.PromoModel;

@Repository
public interface PromoDb extends JpaRepository<PromoModel, Integer> {

    @Query("SELECT promo FROM PromoModel promo WHERE :uuid MEMBER OF promo.stores")
    List<PromoModel> findPromo(@Param("uuid") Integer uuid);

    @Query("SELECT promo FROM PromoModel promo WHERE promo.endDate = :today")
    List<PromoModel> findByEndDate(@Param("today") LocalDate today);
}
