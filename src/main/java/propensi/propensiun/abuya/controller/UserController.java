package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
//        return null; // Jika pengguna tidak ditemukan, kembalikan null
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

    //    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String viewProfile(Model model, Principal principal) {
        UserModel user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("isCOO", user.getPeran().getName().equals("Chief Operating Officer"));

        if (user == null || user.getPeran() == null) {
            model.addAttribute("error", "User or role not found. Please log in.");
            return "redirect:/login"; // Arahkan ke halaman login jika user atau role tidak ditemukan
        }


        String role = user.getPeran().getName();


        if (role.equals("Member") || role.equals("Admin") || role.equals("Marketing") || role.equals("Store Manager") || role.equals("Chief Operating Officer")) {
            model.addAttribute("showEditButton", true);
            model.addAttribute("showDeleteButton", true);
        }


        if (role.equals("Admin")) {
            List<UserModel> cooAndManagers = userService.findCOOAndManagers();
            model.addAttribute("cooAndManagers", cooAndManagers);
        } else if (role.equals("Regional Manager")) {
            List<UserModel> storeManagers = userService.findStoreManagers();
            model.addAttribute("storeManagers", storeManagers);
        }

        return "profile-view";
    }


    @GetMapping(value = "/edit")
    public String editProfileForm(Model model) {
        UserModel user = getUser();
//        if (user == null) {
//            model.addAttribute("error", "User not found.");
//            return "redirect:/login";
//        }
        List<PeranModel> listRole = peranService.findAll(); // Tambahkan peran yang tersedia
        model.addAttribute("listRole", listRole);
        model.addAttribute("user", user); // Tambahkan user ke dalam model
        return "form-edit-profile"; // Template untuk edit profile
    }


    @PostMapping(value = "/edit")
    public String editProfileSubmit(@ModelAttribute UserModel user, Model model) {
        UserModel currentUser = getUser();
        if (currentUser == null || currentUser.getPeran() == null) {
            model.addAttribute("error", "User or role not found.");
            model.addAttribute("user", user); // Tambahkan kembali user jika terjadi error
            return "form-edit-profile"; // Kembali ke form jika user atau role tidak ditemukan
        }


        user.setPeran(currentUser.getPeran());

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(currentUser.getPassword());
        }


        if (user.getCompanyid() == null) {
            user.setCompanyid(currentUser.getCompanyid());
        }

        user.setUuid(currentUser.getUuid()); // Menetapkan ID pengguna saat ini

        // Validasi data input
        String errorMessage = validateUserInput(user);
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            model.addAttribute("user", user);
            return "form-edit-profile";
        }

        // Update profil
        userService.updateUser(user);
        model.addAttribute("success", "Profile has been updated.");
        return "redirect:/user/profile"; // Redirect ke halaman profil
    }



    // Metode validasi input
    private String validateUserInput(UserModel user) {
        if (user.getUsername().length() < 6) {
            return "Username must be at least 6 characters.";
        }
        if (user.getName().length() < 3 || user.getName().matches(".*\\d.*")) {
            return "Name must be at least 3 characters and cannot contain numbers.";
        }

        if (userService.isUsernameTaken(user.getUsername())) {
            return "Username is already taken.";
        }
        return null; // Tidak ada error
    }

    @GetMapping("/delete")
    public String deleteAccountForm(Model model) {
        UserModel user = getUser();
        model.addAttribute("user", user);
        return "form-delete";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam("password") String password, Model model) {
        UserModel user = getUser();
        String userPassword = userService.getPassword(user);

        if (!passwordEncoder.matches(password, userPassword)) {
            model.addAttribute("error", "Password salah. Akun tidak dihapus.");
            return "form-delete";
        }

        String role = user.getPeran().getName();

        if (role.equals("Member") || role.equals("Admin")) {
            userService.deleteUser(user);
            SecurityContextHolder.clearContext(); // Logout setelah akun dihapus
            return "redirect:/user/login";
        }

        model.addAttribute("error", "Anda tidak memiliki izin untuk menghapus akun ini.");
        return "form-delete";
    }

    @GetMapping(value = "/store-manager")
    public String viewStoreManagersPage(Model model) {
        List<UserModel> storeManagers = userService.findStoreManagers();
        model.addAttribute("storeManagers", storeManagers);
        return "store-manager-view";
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