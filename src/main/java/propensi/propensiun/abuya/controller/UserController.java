package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import propensi.propensiun.abuya.model.FeedbackModel;

import propensi.propensiun.abuya.model.FeedbackModel;

import propensi.propensiun.abuya.model.FeedbackModel;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.model.StoreModel;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.service.FeedbackService;
import propensi.propensiun.abuya.service.PeranService;
import propensi.propensiun.abuya.service.StoreService;
import propensi.propensiun.abuya.service.UserService;

import java.lang.reflect.Member;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PeranService peranService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();

                UserModel user = userService.findByUsername(username);
                if (user != null) {
                    return user.getUuid();
                }

                // Jika user tidak ditemukan di database dan username adalah "admin"
                if (username.equals("admin")) {
                    return -1; // ID tetap untuk admin hardcoded
                }
            }
        }
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


                UserModel user = userService.findByUsername(username);
                if (user != null) {
                    return user;
                }


                if (username.equals("admin")) {
                    // Buat objek UserModel
                    UserModel hardcodedAdmin = new UserModel();
                    hardcodedAdmin.setUsername("admin");
                    hardcodedAdmin.setName("Admin");

                    // Set role
                    PeranModel adminRole = new PeranModel();
                    adminRole.setName("Admin");
                    hardcodedAdmin.setPeran(adminRole);

                    // Set UUID
                    hardcodedAdmin.setUuid(-1);

                    return hardcodedAdmin;
                }
            }
        }
        return null;
    }

    // @RequestMapping("/login")
    // public String login() {
    //     return "login";
    // }

    // @GetMapping(value =  "/login")
    // private String login() {
    // @RequestMapping("/login")
    // public String login() {
    //     return "login";
    // }

    @GetMapping(value =  "/login")
    private String login() {
        return "login";
    }

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<PeranModel> listRole = peranService.findAll();

        List<String> securityQuestions = Arrays.asList(
                "Dimana anda lahir?",
                "Siapa nama ibu kandung anda?",
                "Berapa saudara yang anda miliki?"
        );

        Random random = new Random();
        int index = random.nextInt(securityQuestions.size());
        model.addAttribute("security_question", securityQuestions.get(index));

        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);

        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, @RequestParam String passwordConfirmation, Model model) {
        try {
            System.out.println(passwordConfirmation);
            if (!user.getPassword().equals(passwordConfirmation)) {
                throw new Exception("Confirmation password does not match.");
            }
            userService.addUser(user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("message", "User added successfully!");
        }
        catch (Exception e) {
            System.out.println(e.toString());
            model.addAttribute("error", e.getMessage());
        }

        return "add-user";
    }

    @GetMapping(value = "/addMember")
    private String addUserMemberFormPage(Model model) {
        UserModel user = new UserModel();
        List<PeranModel> listRole = peranService.findAll();

        List<String> securityQuestions = Arrays.asList(
                "Dimana anda lahir?",
                "Siapa nama ibu kandung anda?",
                "Berapa saudara yang anda miliki?"
        );

        Random random = new Random();
        int index = random.nextInt(securityQuestions.size());
        model.addAttribute("security_question", securityQuestions.get(index));

        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);

        return "form-add-user-member";
    }

    @PostMapping(value = "/addMember")
    private String addUserMemberSubmit(@ModelAttribute UserModel user, @RequestParam String passwordConfirmation, Model model) {
        try {
            System.out.println(passwordConfirmation);
            if (!user.getPassword().equals(passwordConfirmation)) {
                throw new Exception("Confirmation password does not match.");
            }
            userService.addUser(user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("message", "User added successfully!");
        }
        catch (Exception e) {
            System.out.println(e.toString());
            model.addAttribute("error", e.getMessage());
        }

        return "add-user-member";
    }

    // @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String viewProfile(Model model, Principal principal) {
        UserModel user = getUser();

        model.addAttribute("user", user);
        model.addAttribute("isCOO", user.getPeran().getName().equals("Chief Operating Officer"));

        String role = user.getPeran().getName();
        if (role.equals("Member") || role.equals("Admin") || role.equals("Marketing") || role.equals("Store Manager")
                || role.equals("Chief Operating Officer")) {
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
        List<PeranModel> listRole = peranService.findAll();
        model.addAttribute("listRole", listRole);
        model.addAttribute("user", user);
        return "form-edit-profile";
    }


    @PostMapping(value = "/edit")
    public String editProfileSubmit(@ModelAttribute UserModel user, Model model, RedirectAttributes redirectAttributes) {
        UserModel currentUser = getUser();

        String validationError = validateUserInput(user);
        if (validationError != null) {
            model.addAttribute("error", validationError);
            model.addAttribute("user", currentUser);
            return "form-edit-profile";
        }

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            currentUser.setUsername(user.getUsername());
        }
        if (user.getName() != null && !user.getName().isEmpty()) {
            currentUser.setName(user.getName());
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            currentUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getSecurityQuestion() != null && !user.getSecurityQuestion().isEmpty()) {
            currentUser.setSecurityQuestion(user.getSecurityQuestion());
        }
        if (user.getSecurityAnswer() != null && !user.getSecurityAnswer().isEmpty()) {
            currentUser.setSecurityAnswer(user.getSecurityAnswer());
        }

        userService.updateUser(currentUser);

        UserModel updatedUser = userService.getUserByUsername(currentUser.getUsername());
        model.addAttribute("user", updatedUser);
//        model.addAttribute("message", "Data anda berhasil diganti.");
        redirectAttributes.addFlashAttribute("message", "User updated successfully!");


        return "redirect:/user/edit?success=true";
//        return "redirect:/user/profile?success=true";
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
            SecurityContextHolder.clearContext();
            return "redirect:/";
        }

        model.addAttribute("error", "Anda tidak memiliki izin untuk menghapus akun ini.");
        return "form-delete";
    }

//    @PreAuthorize("hasRole('Store Manager')")
    @GetMapping(value = "/store-manager")
    public String viewStoreManagersPage(Model model) {
        List<UserModel> storeManagers = userService.findStoreManagers();
        model.addAttribute("storeManagers", storeManagers);
        return "store-manager-view";
    }

//    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/user-view-by-admin")
    public String viewUser(Model model) {
        List<UserModel> COO = userService.findCOO();
        model.addAttribute("COO", COO);
        List<UserModel> storeManagers = userService.findStoreManagers();
        model.addAttribute("storeManagers", storeManagers);
        List<UserModel> marketing = userService.findMarketing();
        model.addAttribute("marketing", marketing);

        return "admin-view-user";
    }

    @GetMapping(value = "/ubah-password")
    private String ubahPasswordForm(Model model) {
        UserModel user = getUser();
        String role = user.getPeran().getName();
        model.addAttribute("role", role);
        return "ubah-password";
    }

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

    @GetMapping("/edit-role/{userId}")
    public String editUserRoleForm(@PathVariable Integer userId, Model model) {
        UserModel user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/user/user-view-by-admin";
        }

        List<PeranModel> listRole = peranService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-edit-role";
    }


    @PostMapping("/edit-role/{userId}")
    public String editUserRoleSubmit(@PathVariable Integer userId, @RequestParam("roleId") Integer roleId, Model model) {
        UserModel user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/user/user-view-by-admin";
        }

        PeranModel newRole = peranService.findById(roleId);
        if (newRole == null) {
            model.addAttribute("error", "Role not found.");
            return "redirect:/user/edit-role/" + userId;
        }

        user.setPeran(newRole);
        userService.updateUser(user);

        model.addAttribute("success", "Role updated successfully.");
        return "redirect:/user/user-view-by-admin";
    }

    @PostMapping("/delete-user/{userId}")
    public String deleteUser(@PathVariable Integer userId, Model model) {
        UserModel user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/user/user-view-by-admin";
        }


        userService.deleteUser(user);

        model.addAttribute("success", "User deleted successfully.");
        return "redirect:/user/user-view-by-admin";
    }


    @Autowired
    StoreService storeService;

    @Autowired
    FeedbackService feedbackService;

    @GetMapping(value = "/form-add-feedback")
    private String getFormAddFeedback(Model model) {  
        List<StoreModel> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken == false){
            String role = "Member";
            model.addAttribute("role", role);
        } else {
            String role = null;
            model.addAttribute("role", role);
        }
        
        return "form-add-feedback";
    }

    
    @PostMapping(value = "/form-add-feedback")
    private String postFormAddFeedback (
        @RequestParam("cabang") String storeId,
        @RequestParam("tanggalKunjungan") @DateTimeFormat(pattern = "dd-MMM-yyyy")  LocalDate tanggalKunjungan,
        @RequestParam("waktuPengisian") @DateTimeFormat(pattern = "dd-MMM-yyyy HH:mm:ss") LocalDateTime waktuPengisian,
        @RequestParam("pelayanan") float pelayanan,
        @RequestParam("menu") float menu,
        @RequestParam("fasilitas") float fasilitas,
        @RequestParam("kotakSaran") String kotakSaran,
        RedirectAttributes redirectAttributes,
        Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserModel user;

        if (auth.isAuthenticated()){
            user = getUser();
        } else {
            user = null;
        }

        StoreModel store = storeService.getStoreById(storeId);

        FeedbackModel feedback = new FeedbackModel(null, store, user, pelayanan, menu, fasilitas, kotakSaran, waktuPengisian, tanggalKunjungan);
        System.out.println("Store ID"+store);
        feedbackService.saveFeedback(feedback);
        redirectAttributes.addFlashAttribute("success", "Feedback berhasil ditambahkan");

        return "redirect:/user/form-add-feedback";
    }

    @GetMapping(value = "/feedback-view")
    private String getFeedbackView(Model model) {  
        List<FeedbackModel> feedbacks = feedbackService.getAllFeedback();
        model.addAttribute("feedbacks", feedbacks);  
        return "feedback-view";
    }

    @PostMapping(value = "/feedback-view")
    private String feedbackView (Model model) {
        return "feedback-view";
    }

    @GetMapping(value = "/lupa-password-verif")
    private String verifLupaPassword(Model model) {
        return "lupa-password-verif";
    }

    @PostMapping(value = "/lupa-password-verif")
    private String verifLupaPassword(
            @RequestParam("username") String username,
            Model model) {

        if (userService.isUsernameTaken(username) && username != null && username != "") {
            // Redirect to lupa-password with the username as a path variable
            return "redirect:/user/lupa-password/" + username;
        } else {
            model.addAttribute("error", "Username tidak ditemukan");
            return "lupa-password-verif";
        }
    }
    
    @GetMapping(value = "/lupa-password/{username}")
    private String lupaPassword(@PathVariable("username") String username, Model model) {
        String userSecQuestion = userService.getUserByUsername(username).getSecurityQuestion();
        model.addAttribute("username", username);
        model.addAttribute("userSecQuestion", userSecQuestion);
        System.out.println("TEST SEC QUESTION: "+userSecQuestion);
        return "lupa-password";
    }

    @PostMapping(value = "/lupa-password")
    private String lupaPassword(
        @RequestParam("username") String username,
        @RequestParam("secAnswer") String secAnswer,
        @RequestParam("newPassword") String newPassword,
        @RequestParam("confirmPassword") String confirmPassword,
        Model model,
        RedirectAttributes redirectAttributes
        ){
            UserModel user = userService.getUserByUsername(username);
            String userPassword = userService.getPassword(user);
            String userSecAnswer = user.getSecurityAnswer();

            if (!userSecAnswer.equals(secAnswer)){
                redirectAttributes.addFlashAttribute("error", "The Security Answer is false.");
                return "redirect:/user/lupa-password/" + username;
            }

            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New password and confirm password do not match.");
                return "redirect:/user/lupa-password/" + username;
            }

            if (passwordEncoder.matches(newPassword, userPassword)) {
                redirectAttributes.addFlashAttribute("error", "You can't change the password to the same password.");
                return "redirect:/user/lupa-password/" + username;
            }

            userService.changePassword(user, confirmPassword);
            redirectAttributes.addFlashAttribute("success", "Password has been changed successfully.");

            return "redirect:/user/lupa-password/" + username;  
    }
}