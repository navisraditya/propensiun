package propensi.propensiun.abuya.service;

import propensi.propensiun.abuya.model.MitraModel;

import java.util.List;

public interface MitraService  {

    MitraModel addMitra(MitraModel mitra) throws Exception;
    MitraModel addMitraM(MitraModel mitra) throws Exception;
    List<MitraModel> getListMitra();
}
