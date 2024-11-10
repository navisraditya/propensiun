package propensi.propensiun.abuya.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import propensi.propensiun.abuya.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user) throws Exception;

    UserModel addUserMember(UserModel user) throws Exception;

    public String encrypt(String password);

    public UserModel getUserByUsername(String name);

    public String getPassword(UserModel user);

    UserModel findByUsername(String username);

    void changePassword(UserModel user, String password);

    List<UserModel> findCOOAndManagers();

    List<UserModel> findStoreManagers();

    @Transactional
    void updateUser(UserModel user);

    boolean isUsernameTaken(@NotNull String username);

    @Transactional
    void deleteUser(UserModel user);
}
