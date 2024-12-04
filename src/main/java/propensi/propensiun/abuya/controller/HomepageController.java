package propensi.propensiun.abuya.controller;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import propensi.propensiun.abuya.model.*;
import propensi.propensiun.abuya.service.*;

@Controller
public class HomepageController {
    String logged_user;

    @Autowired
    UserService userService;

    @Autowired
    PromoService promoService;

    @Autowired
    StoreService storeService;

    @Autowired
    MenuService menuService;

    @GetMapping("/")
    public String guestLanding(Model model) {
        return "homepage";
    }

    @GetMapping("/redirectHomepage")
    public ModelAndView redirectHomepage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // fetch auth

        if (auth != null && auth.getAuthorities() != null) {
            logged_user = auth.getName(); // get user-name
            UserModel user = userService.getUserByUsername(logged_user); // find UserModel based on logged user

            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Admin"))) { // role checker on logged user based on UserModel
                System.out.println("admin login");
                return new ModelAndView("redirect:/adminlanding");
            } else {
                if (user != null) {
                    int userRole = user.getPeran().getUuid();

                    if (userRole == 1) {
                        System.out.println("coo login");
                        return new ModelAndView("redirect:/opslanding");
                    } else if (userRole == 2) {
                        System.out.println("sm login");
                        return new ModelAndView("redirect:/smlanding");
                    } else if (userRole == 3) {
                        System.out.println("marketing login");
                        return new ModelAndView("redirect:/marketinglanding");
                    } else if (userRole == 4) {
                        System.out.println("member login");
                        return new ModelAndView("redirect:/memberlanding");
                    } else {
                        System.out.println("guest login");
                        return new ModelAndView("redirect:/");
                    }
                } else {
                    System.out.println("invalid. identified as guest");
                    return new ModelAndView("redirect:/");
                }
            }

        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/adminlanding")
    public String adminLanding(Model model) {
        return "homepage-admin";

    }

    @GetMapping("/memberlanding")
    public String memberLanding(Model model) {
        List<PromoModel> listPromo = promoService.getPromoList(0);
        listPromo.sort(Comparator.comparing(PromoModel::getUuid));
        List<MenuModel> listMenu = menuService.getAllMenus();

        model.addAttribute("promotions",listPromo);
        model.addAttribute("menus", listMenu);
        return "homepage-member";

    }

    @GetMapping("/opslanding")
    public String opsLanding(Model model) {
        return "homepage-ops";

    }

    @GetMapping("/marketinglanding")
    public String marketingLanding(Model model) {
        // for data fetching
        List<PromoModel> listPromo = promoService.getPromoList(0);
        listPromo.sort(Comparator.comparing(PromoModel::getUuid));

        // for adding new promo
        PromoModel promo = new PromoModel();
        List<StoreModel> listStore = storeService.getAllStores();
        

        model.addAttribute("new_promo", promo);
        model.addAttribute("listStore", listStore);
        model.addAttribute("promos", listPromo);
        model.addAttribute("logged_username", logged_user);
        return "homepage-marketing";
    }

    @GetMapping("/smlanding")
    public String storemanagerLanding(Model model) {
        return "homepage-sm";
    }

    @PreAuthorize("hasRole('ROLE_Marketing')")
    @PostMapping("/promo/add")
    public ModelAndView addPromoPageSubmit(@ModelAttribute PromoModel promo, @RequestParam List<Integer> storeList, Model model) {
        List<StoreModel> selectedStoreModels = storeService.getAllStoreByIds(storeList);

        promo.setStores(new HashSet<>(selectedStoreModels));
        promoService.addPromo(promo);
        return new ModelAndView("redirect:/marketinglanding");
    }

    @PreAuthorize("hasRole('ROLE_Marketing')")
    @PostMapping("/promo/delete/{id}")
    public String deleteInTable(@PathVariable Integer id){
        promoService.deletePromoById(id);
        return "redirect:/marketinglanding";
    }

    @PreAuthorize("hasRole('ROLE_Marketing')")
    @PostMapping("/promo/edit/{id}")
    public String editPromo(@PathVariable String id, @ModelAttribute PromoModel sourceModel, @RequestParam List<Integer> storeList) {
        if(!storeList.isEmpty()) {
            List<StoreModel> selecStoreModels = storeService.getAllStoreByIds(storeList);
            
            if(!selecStoreModels.isEmpty()) {
                sourceModel.setStores(new HashSet<>(selecStoreModels));
            }
        }

        promoService.updatePromo(Integer.parseInt(id), sourceModel);
        return "redirect:/marketinglanding";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            UserModel user = userService.getUserByUsername(username);
            model.addAttribute("user", user);
        }

        return "access-denied";
    }
}
