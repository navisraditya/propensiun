package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @GetMapping("/adminlanding")
    public String adminLanding(Model model) {
        if (user != null) {
            return "homepage-admin";
        }

        return "redirect:/login";
    }

    @GetMapping("/memberlanding")
    public String memberLanding(Model model) {
        if (user != null) {
            return "homepage-member";
        }

        return "redirect:/login";
    }

    @GetMapping("/opslanding")
    public String opsLanding(Model model) {
        if (user != null) {
            return "homepage-ops";
        }

        return "redirect:/login";
    }

    @GetMapping("/marketinglanding")
    public String marketingLanding(Model model) {
        if (user != null) {
            return "homepage-marketing";
        }

        return "redirect:/login";
    }

    @GetMapping("/smlanding")
    public String storemanagerLanding(Model model) {
        if (user != null) {
            return "homepage-sm";
        }

        return "redirect:/login";
    }

}
