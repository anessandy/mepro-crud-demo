package com.mepro.democrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MASTER_KARYAWAN")
@NoArgsConstructor
@Getter
@Setter
public class MasterKaryawan implements Serializable {
    @Id
    @Column(name = "IDMASTERKARYAWAN")
    private Long idMasterKaryawan;
    
    @Column(name = "NIK")
    private Long nik;
    
    @Column(name = "NAMALENGKAP")
    private String namaLengkap;
    
    @Column(name = "TGLMULAIKERJA")
    private Date tglMulaiKerja;
    
    @Column(name = "IDDEPARTEMEN")
    private Long idDepartemen;
    
    @Column(name = "SUBDEP_ID")
    private Long subdepId;
}
