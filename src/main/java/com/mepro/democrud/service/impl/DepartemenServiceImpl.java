package com.mepro.democrud.service.impl;

import com.mepro.democrud.entity.Departemen;
import com.mepro.democrud.repository.DepartemenRepository;
import com.mepro.democrud.service.DepartemenService;
import com.mepro.democrud.types.ActiveStatus;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartemenServiceImpl implements DepartemenService {
    
    @Autowired
    private DepartemenRepository departemenRepository;
    
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
    public List<Departemen> searchDepartemen(String kode, String nama) {
        return departemenRepository.searchDepartemen(kode, nama);
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
}
