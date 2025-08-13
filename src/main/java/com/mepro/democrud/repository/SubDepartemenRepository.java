package com.mepro.democrud.repository;

import com.mepro.democrud.entity.SubDepartemen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SubDepartemenRepository extends JpaRepository<SubDepartemen, Long> {
    @Query(value = "SELECT * FROM subdepartemen sd WHERE "
            + "(:idDepartemen IS NULL OR sd.idDepartemen = :idDepartemen) AND "
            + "(:subdepName IS NULL OR sd.subdepName LIKE %:subdepName%) AND sd.status = :status", nativeQuery = true)
    List<SubDepartemen> searchSubDepartemen(@Param("idDepartemen") String idDepartemen, @Param("subdepName") String subdepName, 
            @Param("status") String status);
    
    List<SubDepartemen> findByIdDepartemen(Long idDepartemen);
    List<SubDepartemen> findBySubdepNameContainingIgnoreCase(String subdepName);
    List<SubDepartemen> findByIdDepartemenAndSubdepNameContainingIgnoreCase(Long idDepartemen, String SubdepName);
    
    @Query(value = "SELECT * FROM subdepartemen sd "
            + "WHERE sd.status = :status", nativeQuery = true)
    List<SubDepartemen> listAllSubDepartemen(@Param("status") String status);
    
    @Query("SELECT sd FROM SubDepartemen sd " +
           "WHERE (:query IS NULL OR LOWER(sd.subdepName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<SubDepartemen> searchByNama(@Param("query") String query);
    
}
