package com.mepro.democrud.service.impl;

import com.mepro.democrud.dao.SubDepartemenDao;
import com.mepro.democrud.dto.SubDepartemenDto;
import com.mepro.democrud.entity.SubDepartemen;
import com.mepro.democrud.repository.SubDepartemenRepository;
import com.mepro.democrud.service.SubDepartemenService;
import java.util.ArrayList;
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
}
