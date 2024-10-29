package propensi.propensiun.abuya.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import propensi.propensiun.abuya.model.PromoModel;
import propensi.propensiun.abuya.repository.PromoDb;

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

    @Override
    
    public void deletePromoById(Integer promoUuid) {
        promoDb.deleteById(promoUuid);
    }

}
