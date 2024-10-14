package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.repository.UserDb;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @GetMapping("/redirectHomepage")
    public ModelAndView redirectHomepage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities() != null) {
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
                return new ModelAndView("redirect:/adminlanding");
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Chief Operating Officer"))) {
                return new ModelAndView("redirect:/opslanding");
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Store Manager"))) {
                return new ModelAndView("redirect:/smlanding");
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Marketing"))) {
                return new ModelAndView("redirect:/marketinglanding");
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Member"))) {
                return new ModelAndView("redirect:/memberlanding");
            } else {
                System.out.println("kosong");
                return new ModelAndView("redirect:/user/home");
            }

        }
        return new ModelAndView("redirect:/user/home");
    }

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
}
