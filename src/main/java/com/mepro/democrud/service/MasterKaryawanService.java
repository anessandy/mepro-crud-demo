package com.mepro.democrud.service;

import com.mepro.democrud.dto.MasterKaryawanDto;
import com.mepro.democrud.entity.MasterKaryawan;
import java.util.List;
import java.util.Optional;

public interface MasterKaryawanService {
    List<MasterKaryawanDto> getListMasterKaryawan(Long nik, Long idDepartemen, Long subdepId, String tglMulaiKerja, String namaLengkap);
    Optional<MasterKaryawan> findById(Long id);
}
