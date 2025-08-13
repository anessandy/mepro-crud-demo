package com.mepro.democrud.service.impl;

import com.mepro.democrud.dao.MasterKaryawanDao;
import com.mepro.democrud.dto.MasterKaryawanDto;
import com.mepro.democrud.entity.MasterKaryawan;
import com.mepro.democrud.repository.MasterKaryawanRepository;
import com.mepro.democrud.service.MasterKaryawanService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterKaryawanServiceImpl implements MasterKaryawanService {
    private static final Logger logger = LoggerFactory.getLogger(SubDepartemenServiceImpl.class);
    
    @Autowired
    private MasterKaryawanRepository MasterKaryawanRepository;
    
    @Autowired
    private MasterKaryawanDao masterKaryawanDao;
    
    @Override
    public Optional<MasterKaryawan> findById(Long id) {
        return MasterKaryawanRepository.findById(id);
    }
    
    @Override
    public List<MasterKaryawanDto> getListMasterKaryawan(Long nik, Long idDepartemen, Long subdepId, String tglMulaiKerja, String namaLengkap) {
        List<MasterKaryawanDto> listMasterKaryawan = new ArrayList<>();
        try {
            listMasterKaryawan = masterKaryawanDao.getListMasterKaryawan(nik, tglMulaiKerja, idDepartemen, subdepId, namaLengkap);
        }
        catch (Exception e) {
            logger.error("error on : " + e.getMessage());
        }
        return listMasterKaryawan;
    }
    
}
