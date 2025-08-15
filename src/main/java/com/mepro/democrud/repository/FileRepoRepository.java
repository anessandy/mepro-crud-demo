package com.mepro.democrud.repository;

import com.mepro.democrud.entity.FileRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FileRepoRepository extends JpaRepository<FileRepo, Long> {
    
}
