package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.MitraModel;
import propensi.propensiun.abuya.repository.MitraDb;

import java.util.List;

@Service
public class MitraServiceImpl implements MitraService {

    @Autowired
    private MitraDb mitraDb;

    @Override
    public MitraModel addMitra(MitraModel mitra) throws Exception {
        if (mitra.getName().isEmpty()) {
            throw new Exception("Name must be inputed.");
        }
        if (mitra.getPhoneNumber().isEmpty()) {
            throw new Exception("Phone Number must be inputed.");
        }
        if (mitra.getPhoneNumber().length() < 10 || mitra.getPhoneNumber().length() > 13) {
            throw new Exception("Phone number must consist 10-13 digits.");
        }
        if (mitra.getAlasan().isEmpty()) {
            throw new Exception("Alasan must be inputed.");
        }
        else {
            String nam = mitra.getName();
            mitra.setName(nam);
            return mitraDb.save(mitra);
        }
    }

    @Override
    public MitraModel addMitraM(MitraModel mitra) throws Exception{
        if (mitra.getName().isEmpty()) {
            throw new Exception("Name must be inputed.");
        }
        if (mitra.getPhoneNumber().isEmpty()) {
            throw new Exception("Phone Number must be inputed.");
        }
        if (mitra.getPhoneNumber().length() < 10 || mitra.getPhoneNumber().length() > 13) {
            throw new Exception("Phone number must consist 10-13 digits.");
        }
        if (mitra.getAlasan().isEmpty()) {
            throw new Exception("Alasan must be inputed.");
        }
        else {
            String nam = mitra.getName();
            mitra.setName(nam);
            return mitraDb.save(mitra);
        }
    }

    @Override
    public List<MitraModel> getListMitra(){
        return mitraDb.findAll();
    }
}
