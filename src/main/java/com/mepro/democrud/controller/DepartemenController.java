package com.mepro.democrud.controller;

import com.mepro.democrud.entity.Departemen;
import com.mepro.democrud.service.DepartemenService;
import com.mepro.democrud.types.ActiveStatus;
import com.mepro.democrud.util.SecurityUtil;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/departemen")
public class DepartemenController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private DepartemenService departemenService;
    
    @GetMapping("/index")
    public String list(@RequestParam(name = "kode", required = false) String kode, 
            @RequestParam(name = "nama", required = false) String nama, Model model) {
        logger.info("index departemen");
        List<Departemen> list = departemenService.searchDepartemen(kode, nama);
        model.addAttribute("listDepartemen", list);
        model.addAttribute("kode", kode);
        model.addAttribute("nama", nama);
        return "departemen/index";
    }
    
    /*@GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("departemen", new DepartemenDto());
        return "departemen/add";
    }*/
    
    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        Departemen departemen = new Departemen();
        if (id != null) {
            Optional<Departemen> optional = departemenService.findById(id);
            if (optional.isPresent()) {
                departemen = optional.get();
            }
        }
        model.addAttribute("departemen", departemen);
        return "departemen/form";
    }
    
    @GetMapping("/add")
    public String formAdd(Model model) {
        model.addAttribute("title", "Tambah Departemen Baru");
        model.addAttribute("departemen", new Departemen());
        model.addAttribute("mode", "ADD");
        return "departemen/form";
    }
    
    @GetMapping("/edit")
    public String formEdit(@RequestParam Long id, Model model) {
        logger.info("/edit - iddepartemen = "+id);
        Departemen d = departemenService.findById(id).orElseThrow();
        model.addAttribute("title", "Edit Departemen");
        model.addAttribute("departemen", d);
        model.addAttribute("mode", "EDIT");
        return "departemen/form";
    }
    
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        String userId = SecurityUtil.getCurrentUserId();
        departemenService.updatedDeleteById(id, userId);
        return "redirect:/departemen/index";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute Departemen departemen) {
        departemen.setStatus(ActiveStatus.ACTIVE.getCode());
        departemenService.save(departemen);
        return "redirect:/departemen/index";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute Departemen departemen) {
        departemenService.update(departemen);
        return "redirect:/departemen/index";
    }
}
