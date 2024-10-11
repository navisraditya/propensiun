package propensi.propensiun.abuya.service;

import propensi.propensiun.abuya.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user);

    public String encrypt(String password);


}
