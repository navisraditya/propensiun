package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.service.PeranService;
import propensi.propensiun.abuya.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PeranService peranService;

    private Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                System.out.println(username);
                return userService.findByUsername(username).getUuid();
            }
        }
        // Return nilai default
        return null;
    }

    private UserModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                return userService.findByUsername(username);
            }
        }
        // Return nilai default
        return null;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<PeranModel> listRole = peranService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);

        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        userService.addUser(user);
        model.addAttribute("username", user.getUsername());

        return "add-user";
    }

    @GetMapping(value = "/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName();
        UserModel user = userService.findByUsername(username);
        model.addAttribute("user", user);

        String role = user.getPeran().getName();

        // Display edit and delete buttons for members
        if (role.equals("Member") || role.equals("Admin") || role.equals("Marketing") || role.equals("Store Manager") || role.equals("Chief Operating Officer")) {
            model.addAttribute("showEditButton", true);
            model.addAttribute("showDeleteButton", true);
        }

        // Conditional logic based on roles
        if (user.getPeran().getName().equals("Admin")) {
            List<UserModel> cooAndManagers = userService.findCOOAndManagers();
            model.addAttribute("cooAndManagers", cooAndManagers);
        } else if (user.getPeran().getName().equals("Regional Manager")) {
            List<UserModel> storeManagers = userService.findStoreManagers();
            model.addAttribute("storeManagers", storeManagers);
        }

        return "profile-view";
    }

    @GetMapping(value = "/ubah-password")
    private String ubahPasswordForm(Model model) {
        return "ubah-password";
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping(value = "/ubah-password")
    private String ubahPasswordPost(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        UserModel user = getUser();
        String userPassword = userService.getPassword(user);

        if (!passwordEncoder.matches(oldPassword, userPassword)) {
            model.addAttribute("error", "Password is incorrect.");
            return "ubah-password";
        }

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", "You can't change the password into the same password.");
            return "ubah-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "ubah-password";
        }

        userService.changePassword(user, confirmPassword);
        model.addAttribute("success", "Password has been changed");
        return "ubah-password";
    }

}
