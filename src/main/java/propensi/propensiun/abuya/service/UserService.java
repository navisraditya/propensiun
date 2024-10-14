package propensi.propensiun.abuya.service;

import propensi.propensiun.abuya.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user);

    public String encrypt(String password);



    public UserModel getUserByUsername(String name);

    UserModel findByUsername(String username);

    void changePassword(UserModel user, String password);

}
