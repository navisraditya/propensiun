package propensi.propensiun.abuya.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
<<<<<<< HEAD

    @Override
    public PromoModel updatePromo(Integer id, PromoModel sourcePromo) {
        PromoModel targetPromo = findPromoById(id);
        BeanUtils.copyProperties(sourcePromo, targetPromo);

        return promoDb.save(targetPromo);
    }

    @Override
    public PromoModel findPromoById(Integer promoUuid) {
        return promoDb.findById(promoUuid).orElse(null);
    }
    
=======
>>>>>>> af3b03fb56ff261428b9e23b738abd8d5900141e
}
