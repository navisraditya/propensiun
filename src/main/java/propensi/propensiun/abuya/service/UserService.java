package propensi.propensiun.abuya.service;

import jakarta.validation.constraints.NotNull;
import propensi.propensiun.abuya.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);

    public String encrypt(String password);

    public UserModel getUserByUsername(String name);
    
    public String getPassword(UserModel user);

    UserModel findByUsername(String username);

    void changePassword(UserModel user, String password);

    List<UserModel> findCOOAndManagers();

    List<UserModel> findStoreManagers();
}
