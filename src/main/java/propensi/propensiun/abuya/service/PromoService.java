package propensi.propensiun.abuya.service;

import java.util.List;

import propensi.propensiun.abuya.model.PromoModel;

public interface PromoService {
    PromoModel addPromo(PromoModel promo);

    List<PromoModel> getPromoList(Integer storeUuid);

    void deletePromoById(Integer promoUuid);
}
