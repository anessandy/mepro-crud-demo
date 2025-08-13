package com.mepro.democrud.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MasterKaryawanDto {
    private Long idMasterKaryawan;
    private Long nik;
    private String namaLengkap;
    private String tglMulaiKerja;
    private String namaDepartemen;
    private String subdepName;
}
