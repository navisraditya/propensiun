package propensi.propensiun.abuya.service;

import java.util.List;

import propensi.propensiun.abuya.model.PromoModel;

public interface PromoService {
    PromoModel addPromo(PromoModel promo);

    PromoModel findPromoById(Integer promoUuid);

    List<PromoModel> getPromoList(Integer storeUuid);

    void deletePromoById(Integer promoUuid);

    void deletePromoOnDate();
    
    void updatePromo(PromoModel promo);
}
