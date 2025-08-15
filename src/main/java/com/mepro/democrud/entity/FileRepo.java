package com.mepro.democrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FILEREPO")
@NoArgsConstructor
@Getter
@Setter
public class FileRepo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDFILEREPO_SEQ")
    @SequenceGenerator(name = "IDFILEREPO_SEQ", sequenceName = "IDFILEREPO_SEQ", allocationSize = 1)
    @Column(name = "IDFILEREPO")
    private Long idFileRepo;
    
    @Column(name = "FILENAME")
    private String fileName;
    
    @Column(name = "FILEEXT")
    private String fileExt;
    
    @Column(name = "STATUS")
    private String status;
}
