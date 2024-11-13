package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.repository.PeranDb;

import java.util.List;

@Service
public class PeranServiceImpl implements PeranService {

    @Autowired
    private PeranDb peranDb;

    @Override
    public List<PeranModel> findAll() {
        return peranDb.findAll();
    }

    @Override
    public void addPeran(PeranModel peran) {
        peranDb.save(peran);
    }

    @Override
    public PeranModel findById(Integer id) {
        return  peranDb.findById((long)id).orElse(null);
    }
}
