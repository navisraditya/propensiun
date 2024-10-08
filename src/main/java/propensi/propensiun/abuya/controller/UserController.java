package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.propensiun.abuya.model.PeranModel;
import propensi.propensiun.abuya.model.UserModel;
import propensi.propensiun.abuya.service.PeranService;
import propensi.propensiun.abuya.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PeranService peranService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping(value="/home")
    private String home(){
        return "homepage";
    }


    @GetMapping(value="/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<PeranModel> listRole = peranService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);

        return "form-add-user";
    }

    @PostMapping(value="/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        userService.addUser(user);
        model.addAttribute("username", user.getUsername());

        return "add-user";
    }
}
