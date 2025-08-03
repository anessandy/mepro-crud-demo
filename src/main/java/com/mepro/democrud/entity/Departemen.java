package com.mepro.democrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DEPARTEMEN")
@NoArgsConstructor
@Getter
@Setter
public class Departemen implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDDEPARTEMEN_SEQ")
    @SequenceGenerator(name = "IDDEPARTEMEN_SEQ", sequenceName = "IDDEPARTEMEN_SEQ", allocationSize = 1)
    @Column(name = "IDDEPARTEMEN")
    private Long idDepartemen;
    
    @Column(name = "NAMADEPARTEMEN")
    private String namaDepartemen;
    
    @Column(name = "CODEDEPT")
    private String codeDept;
    
    @Column(name = "ISOFFICE")
    private Long isOffice;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "CREATEDBY")
    private String createdBy;
    
    @Column(name = "DATECREATED")
    private Date dateCreated;
    
    @Column(name = "UPDATEDBY")
    private String updatedBy;
    
    @Column(name = "DATEUPDATED")
    private Date dateUpdated;
}
