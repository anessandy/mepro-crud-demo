package com.mepro.democrud.service;

import com.mepro.democrud.entity.Departemen;
import java.util.List;
import java.util.Optional;

public interface DepartemenService {
    List<Departemen> findAll();
    Optional<Departemen> findById(Long id);
    List<Departemen> searchDepartemen(String kode, String nama);
    Departemen save(Departemen departemen);
    void updatedDeleteById(Long id, String updatedby);
    void update(Departemen departemen);
}
