package com.mepro.democrud.controller;

import com.mepro.democrud.dto.MasterKaryawanDto;
import com.mepro.democrud.entity.Departemen;
import com.mepro.democrud.entity.SubDepartemen;
import com.mepro.democrud.service.DepartemenService;
import com.mepro.democrud.service.MasterKaryawanService;
import com.mepro.democrud.service.SubDepartemenService;
import com.mepro.democrud.types.ActiveStatus;
import java.io.Serializable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/masterkaryawan")
public class MasterKaryawanController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MasterKaryawanController.class);

    @Autowired
    private MasterKaryawanService masterKaryawanService;

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
        logger.info("index master karyawan");
        List<MasterKaryawanDto> list = masterKaryawanService.getListMasterKaryawan(null, null, null, null, null);
        model.addAttribute("listMasterKaryawan", list);
        return "masterkaryawan/index";
    }

    @GetMapping("/search")
    public String search(Model model,
            @RequestParam(name = "nik", required = false) Long nik,
            @RequestParam(name = "namaLengkap", required = false) String namaLengkap,
            @RequestParam(name = "tglMulaiKerja", required = false) String tglMulaiKerja,
            @RequestParam(name = "idDepartemen", required = false) Long idDepartemen,
            @RequestParam(name = "subdepId", required = false) Long subdepId) {
        logger.info("search sub departemen");
        model.addAttribute("nik", nik);
        model.addAttribute("namaLengkap", namaLengkap);
        model.addAttribute("tglMulaiKerja", tglMulaiKerja);
        model.addAttribute("idDepartemen", idDepartemen);
        model.addAttribute("subdepId", subdepId);

        if (idDepartemen != null) {
            model.addAttribute("namaDepartemenSelected", departemenService.findById(idDepartemen).get().getNamaDepartemen());
        }

        if (subdepId != null) {
            model.addAttribute("namaSubDepartemenSelected", subDepartemenService.findById(subdepId).get().getSubdepName());
        }

        List<MasterKaryawanDto> listMasterKaryawan = masterKaryawanService.getListMasterKaryawan(nik, idDepartemen, subdepId, tglMulaiKerja, namaLengkap);
        model.addAttribute("listMasterKaryawan", listMasterKaryawan);
        return "masterkaryawan/index";
    }

    @GetMapping("/listDepartemen")
    @ResponseBody
    public List<Map<String, String>> listDepartemen(@RequestParam(value = "q", required = false) String query) {
        logger.info("listDepartemen, query = {}", query);
        List<Departemen> list;

        if (query != null && !query.isBlank()) {
            list = departemenService.searchByNamaOrKode(query);
        } else {
            list = departemenService.listAllDepartemen(ActiveStatus.ACTIVE.getCode());
        }
        return list.stream()
                .map(d -> Map.of(
                "id", String.valueOf(d.getIdDepartemen()),
                "text", d.getNamaDepartemen()
        ))
                .toList();
    }

    @GetMapping("/listSubDepartemen")
    @ResponseBody
    public List<Map<String, String>> listSubDepartemen(@RequestParam(value = "q", required = false) String query) {
        logger.info("listSubDepartemen, query = {}", query);
        List<SubDepartemen> list;

        if (query != null && !query.isBlank()) {
            list = subDepartemenService.searchByNama(query);
        } else {
            list = subDepartemenService.listAllSubDepartemen(ActiveStatus.ACTIVE.getCode());
        }
        return list.stream()
                .map(d -> Map.of(
                "id", String.valueOf(d.getSubdepId()),
                "text", d.getSubdepName()
        ))
                .toList();
    }
}
