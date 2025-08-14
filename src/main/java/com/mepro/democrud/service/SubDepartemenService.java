package com.mepro.democrud.service;

import com.mepro.democrud.dto.SubDepartemenDto;
import com.mepro.democrud.entity.SubDepartemen;
import java.util.List;
import java.util.Optional;

public interface SubDepartemenService {
    List<SubDepartemen> findAll();
    Optional<SubDepartemen> findById(Long id);
    List<SubDepartemenDto> getListSubDepartemen(Long subdepId, Long idDepartemen, String subdepName);
    SubDepartemen save(SubDepartemen subDepartemen);
    void update(SubDepartemen subDepartemen);
    void updatedDeleteById(Long id, String updatedBy);
    List<SubDepartemen> listAllSubDepartemen(String status);
    List<SubDepartemen> searchByNama(String query);
    List<SubDepartemen> findByIdDepartemen(Long idDepartemen);
}
