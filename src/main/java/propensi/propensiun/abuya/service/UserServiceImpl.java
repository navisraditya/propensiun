package propensi.propensiun.abuya.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Null;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.repository.UserDb;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) throws Exception {
        if (user.getPeran() == null) {
            throw new Exception("Please choose peran.");
        }
        if (user.getSecurityAnswer() == null) {
            throw new Exception("Please answer the security question.");
        }
        if (user.getName().length() < 3) {
            throw new Exception("Name must consist at least 3 characters.");
        }
        if (user.getName().matches(".*\\d.*")) {
            throw new Exception("Name shouldn't contain numeric character.");
        }
        if (user.getUsername().length() < 6) {
            throw new Exception("Username must consist at least 6 characters.");
        }
        if (user.getPassword().length() < 8) {
            throw new Exception("Password must consist at least 8 characters.");
        }
        if (user.getPhoneNumber().length() < 10 || user.getPhoneNumber().length() > 13) {
            throw new Exception("Phone number must consist 10-13 digits.");
        }
        if (!user.getPassword().matches(".*\\d.*")) {
            throw new Exception("Password must consist at least 1 numeric character.");
        }
        if (!user.getPassword().matches(".*[A-Z].*")) {
            throw new Exception("Password must consist at least 1 uppercase character.");
        }
        if (user.getSecurityAnswer().isEmpty()) {
            throw new Exception("Security answer must be inputed.");
        }


        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        try {
            return userDb.save(user);
        }
        catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = violations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            throw new Exception(errorMessage);
        }
        catch (DataIntegrityViolationException e) {
            throw new Exception("The username must be unique. Please try another username.");
        }

    }

    @Override
    public UserModel addUserMember(UserModel user) throws Exception {
        if (user.getPeran() == null) {
            throw new Exception("Please choose peran.");
        }
        if (user.getSecurityAnswer() == null) {
            throw new Exception("Please answer the security question.");
        }
        if (user.getName().length() < 3) {
            throw new Exception("Name must consist at least 3 characters.");
        }
        if (user.getName().matches(".*\\d.*")) {
            throw new Exception("Name shouldn't contain numeric character.");
        }
        if (user.getUsername().length() < 6) {
            throw new Exception("Username must consist at least 6 characters.");
        }
        if (user.getPassword().length() < 8) {
            throw new Exception("Password must consist at least 8 characters.");
        }
        if (user.getPhoneNumber().length() < 10 || user.getPhoneNumber().length() > 13) {
            throw new Exception("Phone number must consist 10-13 digits.");
        }
        if (!user.getPassword().matches(".*\\d.*")) {
            throw new Exception("Password must consist at least 1 numeric character.");
        }
        if (!user.getPassword().matches(".*[A-Z].*")) {
            throw new Exception("Password must consist at least 1 uppercase character.");
        }
        if (user.getSecurityAnswer().isEmpty()) {
            throw new Exception("Security answer must be inputed.");
        }

        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        try {
            return userDb.save(user);
        }
        catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = violations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            throw new Exception(errorMessage);
        }
        catch (DataIntegrityViolationException e) {
            throw new Exception("The username must be unique. Please try another username.");
        }

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
    }

    public UserModel getUserByUsername(String name) {
        return userDb.findByUsername(name);
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

    @Override
    public String getPassword(UserModel user) {
        return user.getPassword();
    }

    @Override
    public List<UserModel> findCOOAndManagers() {
        return userDb.findByRoleNames(Arrays.asList("COO", "Regional Manager", "Store Manager"));
    }

    @Override
    public List<UserModel> findMarketing(){
        return userDb.findMarketing();
    }

    @Override
    public List<UserModel> findCOO(){
        return userDb.findCOO();
    }

    @Override
    public List<UserModel> findStoreManagers() {
        return userDb.findStoreManagers();
    }

    public void updateUser(UserModel user) {
        userDb.save(user);
    }

    public boolean isUsernameTaken(String username) {
        UserModel existingUser = userDb.findByUsername(username);
        return existingUser != null;
    }

    @Override
    @Transactional
    public void deleteUser(UserModel user) {
        userDb.delete(user);
    }

}
