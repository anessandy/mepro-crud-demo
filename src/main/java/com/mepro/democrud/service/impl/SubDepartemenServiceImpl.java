package com.mepro.democrud.service.impl;

import com.mepro.democrud.dao.SubDepartemenDao;
import com.mepro.democrud.dto.SubDepartemenDto;
import com.mepro.democrud.entity.SubDepartemen;
import com.mepro.democrud.repository.SubDepartemenRepository;
import com.mepro.democrud.service.SubDepartemenService;
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
public class SubDepartemenServiceImpl implements SubDepartemenService {
    private static final Logger logger = LoggerFactory.getLogger(SubDepartemenServiceImpl.class);
    
    @Autowired
    private SubDepartemenRepository subDepartemenRepository;
    
    @Autowired
    private SubDepartemenDao subDepartemenDao;
    
    @Override
    public List<SubDepartemen> findAll() {
        return subDepartemenRepository.findAll();
    }
    
    @Override
    public Optional<SubDepartemen> findById(Long id) {
        return subDepartemenRepository.findById(id);
    }
    
    @Override
    public List<SubDepartemenDto> getListSubDepartemen(Long subdepId, Long idDepartemen, String subdepName) {
        List<SubDepartemenDto> listSubDepartemen = new ArrayList<>();
        try {
            listSubDepartemen = subDepartemenDao.getListSubDepartemen(subdepId, idDepartemen, subdepName);
        }
        catch (Exception e) {
            logger.error("error on : " + e.getMessage());
        }
        return listSubDepartemen;
    }
    
    @Override
    public SubDepartemen save(SubDepartemen subDepartemen) {
        return subDepartemenRepository.save(subDepartemen);
    }
    
    @Override
    public void update(SubDepartemen subDepartemen) {
        SubDepartemen existing = subDepartemenRepository.findById(subDepartemen.getSubdepId()).orElseThrow();
        existing.setSubdepName(subDepartemen.getSubdepName());
        existing.setIdDepartemen(subDepartemen.getIdDepartemen());
        existing.setStatus(ActiveStatus.ACTIVE.getCode());
        subDepartemenRepository.save(existing);
    }
    
    @Override
    public void updatedDeleteById(Long id, String updatedBy) {
        Optional<SubDepartemen> opt = subDepartemenRepository.findById(id);
        if (opt.isPresent()) {
            SubDepartemen subdep = opt.get();
            subdep.setStatus(ActiveStatus.INACTIVE.getCode());
            subdep.setUpdatedBy(updatedBy);
            subdep.setDateUpdated(new Date());
            subDepartemenRepository.save(subdep);
        }
    }
}
