package propensi.propensiun.abuya.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import propensi.propensiun.abuya.model.PromoModel;
import propensi.propensiun.abuya.repository.PromoDb;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDb promoDb;

    @Override
    public PromoModel addPromo(PromoModel promo) {
        return promoDb.save(promo);
    }

    @Override
    public List<PromoModel> getPromoList(Integer storeUuid) {
        if (storeUuid == null || storeUuid == 0) {
            return promoDb.findAll();
        }
        return promoDb.findPromo(storeUuid);
    }

    public void deletePromoById(Integer promoUuid) {
        promoDb.deleteById(promoUuid);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void deletePromoOnDate() {
        LocalDate currDate = LocalDate.now();

        List<PromoModel> expiredPromos = promoDb.findByEndDate(currDate);

        for (PromoModel promo : expiredPromos) {
            if (promo.getEndDate() != null && promo.getEndDate().equals(currDate)) {
                deletePromoById(promo.getUuid());
            }
        }
    }

    @Override
    public void updatePromo(PromoModel promo) {
        Optional<PromoModel> existingPromo = promoDb.findById(promo.getUuid());
        if (existingPromo.isPresent()) {
            PromoModel updatedPromo = existingPromo.get();
            updatedPromo.setName(promo.getName());
            updatedPromo.setDescription(promo.getDescription());
            updatedPromo.setCode(promo.getCode());
            updatedPromo.setStartDate(promo.getStartDate());
            updatedPromo.setEndDate(promo.getEndDate());
            updatedPromo.setStores(promo.getStores());
            
            promoDb.save(updatedPromo);
        }
    }

    @Override
    public PromoModel findPromoById(Integer promoUuid) {
        return promoDb.getReferenceById(promoUuid);
    }
    
}
