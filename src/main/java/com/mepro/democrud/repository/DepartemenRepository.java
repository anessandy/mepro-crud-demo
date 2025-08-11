package com.mepro.democrud.repository;

import com.mepro.democrud.entity.Departemen;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
@Transactional
public interface DepartemenRepository extends JpaRepository<Departemen, Long> {
    List<Departemen> findByNamaDepartemenContainingIgnoreCase(String nama);
    List<Departemen> findByStatus(String status);
    List<Departemen> findByCodeDept(String codeDept);
    
    @Override
    Optional<Departemen> findById(Long id);
    
    @Query(value = "SELECT * FROM departemen d "
            + "WHERE d.status = :status", nativeQuery = true)
    List<Departemen> listAllDepartemen(@Param("status") String status);
    
    @Query("SELECT d FROM Departemen d " +
           "WHERE (:query IS NULL OR LOWER(d.codeDept) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "   OR LOWER(d.namaDepartemen) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Departemen> searchByNamaOrKode(@Param("query") String query);
}
