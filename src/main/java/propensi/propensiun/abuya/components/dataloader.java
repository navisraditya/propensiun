package propensi.propensiun.abuya.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.repository.PeranDb;

import java.util.ArrayList;

@Component
public class dataloader implements ApplicationRunner {
    private PeranDb peranDb;

    @Autowired
    public dataloader(PeranDb peranDb) {
        this.peranDb = peranDb;
    }

    public void run(ApplicationArguments args) {
        peranDb.save(new PeranModel(1, "Chief Operating Officer", new ArrayList<UserModel>()));
        peranDb.save(new PeranModel(2, "Store Manager", new ArrayList<UserModel>()));
        peranDb.save(new PeranModel(3, "Marketing", new ArrayList<UserModel>()));
        peranDb.save(new PeranModel(4, "Member", new ArrayList<UserModel>()));
    }
}
