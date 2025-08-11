package com.mepro.democrud.service.impl;

import com.mepro.democrud.dao.DepartemenDao;
import com.mepro.democrud.dto.DepartemenDto;
import com.mepro.democrud.entity.Departemen;
import com.mepro.democrud.repository.DepartemenRepository;
import com.mepro.democrud.service.DepartemenService;
import com.mepro.democrud.types.ActiveStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartemenServiceImpl implements DepartemenService {
    private static final Logger logger = LoggerFactory.getLogger(DepartemenServiceImpl.class);
    
    @Autowired
    private DepartemenRepository departemenRepository;
    
    @Autowired
    private DepartemenDao departemenDao;
    
    @Override
    public List<Departemen> findAll() {
        return departemenRepository.findAll();
    }
    
    @Override
    public Optional<Departemen> findById(Long id) {
        return departemenRepository.findById(id);
    }

    @Override
    public Departemen save(Departemen departemen) {
        return departemenRepository.save(departemen);
    }
    
    @Override
    public void update(Departemen departemen) {
        Departemen existing = departemenRepository.findById(departemen.getIdDepartemen()).orElseThrow();
        existing.setNamaDepartemen(departemen.getNamaDepartemen());
        existing.setCodeDept(departemen.getCodeDept());
        departemenRepository.save(existing);
    }
    
    @Override
    public void updatedDeleteById(Long id, String updatedBy) {
        Optional<Departemen> opt = departemenRepository.findById(id);
        if (opt.isPresent()) {
            Departemen dep = opt.get();
            dep.setStatus(ActiveStatus.INACTIVE.getCode());
            dep.setUpdatedBy(updatedBy);
            dep.setDateUpdated(new Date());
            departemenRepository.save(dep);
        }
    }
    
    @Override
    public List<DepartemenDto> getListDepartemen(Long idDepartemen, String codeDept, String namaDepartemen) {
        List<DepartemenDto> listDepartemen = new ArrayList<>(); 
        try {
            listDepartemen = departemenDao.getListDepartemen(idDepartemen, codeDept, namaDepartemen);
        } catch (Exception e) {
            logger.error("error on : " + e.getMessage());
        }
        return listDepartemen;
    }
    
    // Add For Sub Departemen
    @Override
    public List<Departemen> listAllDepartemen(String status) {
        return departemenRepository.listAllDepartemen(status);
    }
    
    @Override
    public List<Departemen> searchByNamaOrKode(String query) {
        return departemenRepository.searchByNamaOrKode(query);
    }
}
