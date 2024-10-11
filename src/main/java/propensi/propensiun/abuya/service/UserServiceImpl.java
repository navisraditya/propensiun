package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.repository.UserDb;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public UserModel findByUsername(String username) {
        return userDb.findByUsername(username);

    public UserModel getUserByUsername(String name) {
        return userDb.findByUsername(name);
    }

    public UserModel findByUsername(String username) {
        UserModel user = userDb.findByUsername(username);
        return user;
    }

    @Override
    // @Transactional
    public void changePassword(UserModel user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPassword = passwordEncoder.encode(password);
        user.setPassword(newPassword);
        try {
            userDb.save(user);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            System.err.println("Failed to update password: " + e.getMessage());
        }
    }
}
