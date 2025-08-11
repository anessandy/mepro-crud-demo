package com.mepro.democrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SUBDEPARTEMEN")
@NoArgsConstructor
@Getter
@Setter
public class SubDepartemen implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDSUBDEPARTEMEN_SEQ")
    @SequenceGenerator(name = "IDSUBDEPARTEMEN_SEQ", sequenceName = "IDSUBDEPARTEMEN_SEQ", allocationSize = 1)
    @Column(name = "SUBDEP_ID")
    private Long subdepId;
    
    @Column(name = "SUBDEP_NAME")
    private String subdepName;
    
    @Column(name = "IDDEPARTEMEN")
    private Long idDepartemen;
    
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
