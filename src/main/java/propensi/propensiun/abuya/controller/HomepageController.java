package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import propensi.propensiun.abuya.model.*;
import propensi.propensiun.abuya.service.*;

@Controller
public class HomepageController {
    UserModel user;

    @Autowired
    UserService userService;

    @GetMapping("/redirectHomepage")
    public ModelAndView redirectHomepage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities() != null) {
            String userName = auth.getName();
            UserModel user = userService.getUserByUsername(userName);

            if (user != null) {
                int userRole = user.getPeran().getUuid();
                if (userRole == 0) {
                    return new ModelAndView("redirect:/adminlanding");
                } else if (userRole == 1) {
                    return new ModelAndView("redirect:/opslanding");
                } else if (userRole == 2) {
                    return new ModelAndView("redirect:/smlanding");
                } else if (userRole == 3) {
                    return new ModelAndView("redirect:/marketinglanding");
                } else if (userRole == 4) {
                    return new ModelAndView("redirect:/memberlanding");
                } else {
                    System.out.println("gk kenal");
                    return new ModelAndView("redirect:/user/home");
                }
            } else {
                System.out.println("kosong");
                return new ModelAndView("redirect:/user/home");
            }

        }
        return new ModelAndView("redirect:/user/home");
    }

    @GetMapping("/adminlanding")
    public String adminLanding(Model model) {
        return "homepage-admin";

    }

    @GetMapping("/memberlanding")
    public String memberLanding(Model model) {
        return "homepage-member";

    }

    @GetMapping("/opslanding")
    public String opsLanding(Model model) {
        return "homepage-ops";

    }

    @GetMapping("/marketinglanding")
    public String marketingLanding(Model model) {
        return "homepage-marketing";
    }

    @GetMapping("/smlanding")
    public String storemanagerLanding(Model model) {
        return "homepage-sm";
    }

}
