package propensi.propensiun.abuya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.propensiun.abuya.model.MitraModel;

import propensi.propensiun.abuya.service.MitraService;
import propensi.propensiun.abuya.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/mitra")
public class MitraController {

    @Autowired
    private MitraService mitraService;

    @GetMapping(value = "/add")
    private String addMitraFormPage(Model model) {
        MitraModel mitra = new MitraModel();

        model.addAttribute("mitra", mitra);

        return "form-add-mitra";
    }

    @PostMapping(value="/add")
    private String addMitraSubmit(@ModelAttribute MitraModel mitra, Model model) throws Exception {
        try {
            mitraService.addMitra(mitra);
            model.addAttribute("name", mitra.getName());
            model.addAttribute("message", "mitra added successfully!");
        }
        catch (Exception e) {
            System.out.println(e.toString());
            model.addAttribute("error", e.getMessage());
        }

        return "add-mitra";
    }

    @GetMapping(value = "/addMitra")
    private String addMitraMFormPage(Model model) {
        MitraModel mitra = new MitraModel();

        model.addAttribute("mitra", mitra);

        return "form-add-mitra-member";
    }

    @PostMapping(value="/addMitra")
    private String addMitraMSubmit(@ModelAttribute MitraModel mitra, Model model) throws Exception {
        try {
            mitraService.addMitraM(mitra);
            model.addAttribute("name", mitra.getName());
            model.addAttribute("message", "mitra added successfully!");
        }
        catch (Exception e) {
            System.out.println(e.toString());
            model.addAttribute("error", e.getMessage());
        }

        return "add-mitra-member";
    }

    @GetMapping(value="")
    public String mitraCourse(Model model) {
        List<MitraModel> listMitra = mitraService.getListMitra();
        model.addAttribute("listMitra", listMitra);
        return "viewall-mitra";
    }
}
