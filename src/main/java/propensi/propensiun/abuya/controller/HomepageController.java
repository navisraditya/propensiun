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
            String currAccName = auth.getName();
            user = userService.getUserByUsername(currAccName);

            if (auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("admin"))) {
                return new ModelAndView("redirect:/memberlanding");
            }
            if (auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("member"))) {
                return new ModelAndView("redirect:/memberlanding");
            }
            if (auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("Store Manager"))) {
                return new ModelAndView("redirect:/memberlanding");
            }
            if (auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("Marketing"))) {
                return new ModelAndView("redirect:/memberlanding");
            }
            if (auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("Chief Operating Officer"))) {
                return new ModelAndView("redirect:/memberlanding");
            }

        }
        return new ModelAndView("redirect:/login");
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

}
