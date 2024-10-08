package propensi.propensiun.abuya.service;

import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.PeranModel;

import java.util.List;


public interface PeranService {
    List<PeranModel> findAll();
    void addPeran(PeranModel peran);
}
