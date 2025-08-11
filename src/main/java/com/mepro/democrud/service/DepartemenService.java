package com.mepro.democrud.service;

import com.mepro.democrud.dto.DepartemenDto;
import com.mepro.democrud.entity.Departemen;
import java.util.List;
import java.util.Optional;

public interface DepartemenService {
    List<Departemen> findAll();
    Optional<Departemen> findById(Long id);
    Departemen save(Departemen departemen);
    void updatedDeleteById(Long id, String updatedby);
    void update(Departemen departemen);
    List<DepartemenDto> getListDepartemen(Long idDepartemen, String codeDept, String namaDepartemen);
    List<Departemen> listAllDepartemen(String status);
    List<Departemen> searchByNamaOrKode(String query);
}
