package com.mepro.democrud.controller;

import com.mepro.democrud.dto.SubDepartemenDto;
import com.mepro.democrud.entity.Departemen;
import com.mepro.democrud.entity.SubDepartemen;
import com.mepro.democrud.service.DepartemenService;
import com.mepro.democrud.service.SubDepartemenService;
import com.mepro.democrud.types.ActiveStatus;
import com.mepro.democrud.util.SecurityUtil;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/subdepartemen")
public class SubDepartemenController {
    private static final Logger logger = LoggerFactory.getLogger(DepartemenController.class);
    
    @Autowired
    private DepartemenService departemenService;
    
    @Autowired
    private SubDepartemenService subDepartemenService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    @GetMapping("/index")
    public String index(Model model) {
        logger.info("index sub departemen");
        List<SubDepartemenDto> list = subDepartemenService.getListSubDepartemen(null, null, null);
        model.addAttribute("listSubDepartemen", list);
        return "subdepartemen/index";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(name = "idDepartemen", required = false) Long idDepartemen, 
            @RequestParam(name = "subdepName", required = false) String subdepName, Model model) {
        logger.info("index sub departemen");
        model.addAttribute("idDepartemen", idDepartemen);
        model.addAttribute("subdepName", subdepName);
        if (idDepartemen != null) {
            logger.info("namaDepartemenSelected : " + departemenService.findById(idDepartemen).get().getNamaDepartemen());
            model.addAttribute("namaDepartemenSelected", departemenService.findById(idDepartemen).get().getNamaDepartemen());
        }
        List<SubDepartemenDto> list = subDepartemenService.getListSubDepartemen(null, idDepartemen, subdepName);
        model.addAttribute("listSubDepartemen", list);
        return "subdepartemen/index";
    }
    
    @GetMapping("/add")
    public String add(Model model) {
        List<Departemen> list = departemenService.listAllDepartemen(ActiveStatus.ACTIVE.getCode());
        model.addAttribute("title", "Tambah Sub Departemen Baru");
        model.addAttribute("subdepartemen", new SubDepartemen());
        model.addAttribute("listDepartemen", list);
        model.addAttribute("mode", "ADD");
        return "subdepartemen/form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute SubDepartemen subDepartemen) {
        subDepartemen.setStatus(ActiveStatus.ACTIVE.getCode());
        subDepartemenService.save(subDepartemen);
        return "redirect:/subdepartemen/index";
    }
    
    @GetMapping("/listDepartemen")
    @ResponseBody
    public List<Map<String, String>> listDepartemen(@RequestParam(value = "q", required = false) String query) {
        logger.info("listDepartemen, query = {}", query);
        List<Departemen> list;

        if (query != null && !query.isBlank()) {
            list = departemenService.searchByNamaOrKode(query);
        } 
        else {
            list = departemenService.listAllDepartemen(ActiveStatus.ACTIVE.getCode());
        }
        return list.stream()
            .map(d -> Map.of(
                    "id", String.valueOf(d.getIdDepartemen()),
                    "text", d.getNamaDepartemen()
            ))
            .toList();
    }
    
    @GetMapping("/edit")
    public String formEdit(@RequestParam Long id, Model model) {
        logger.info("/edit - subdepId = "+id);
        SubDepartemen sd = subDepartemenService.findById(id).orElseThrow();
        model.addAttribute("title", "Edit Sub Departemen");
        model.addAttribute("subdepartemen", sd);
        model.addAttribute("mode", "EDIT");
        model.addAttribute("idDepartemen", sd.getIdDepartemen());
        model.addAttribute("namaDepartemenSelected", departemenService.findById(sd.getIdDepartemen()).get().getNamaDepartemen());
        return "subdepartemen/form";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute SubDepartemen sd) {
        subDepartemenService.update(sd);
        return "redirect:/subdepartemen/index";
    }
    
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        String userId = SecurityUtil.getCurrentUserId();
        subDepartemenService.updatedDeleteById(id, userId);
        return "redirect:/subdepartemen/index";
    }
}
