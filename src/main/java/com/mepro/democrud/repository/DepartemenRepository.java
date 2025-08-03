package com.mepro.democrud.repository;

import com.mepro.democrud.entity.Departemen;
import com.mepro.democrud.types.ActiveStatus;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
@Transactional
public interface DepartemenRepository extends JpaRepository<Departemen, Long>{
    List<Departemen> findByNamaDepartemenContainingIgnoreCase(String nama);
    List<Departemen> findByStatus(String status);
    
    @Query("SELECT d FROM Departemen d WHERE "
            + "(:kode IS NULL OR d.codeDept LIKE %:kode%) AND" 
            + "(:nama IS NULL OR d.namaDepartemen LIKE %:nama%) AND d.status = 'A' ")
    List<Departemen> searchDepartemen(@Param("kode") String kode, @Param("nama") String nama);
}
