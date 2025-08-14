package com.mepro.democrud.dao;

import com.mepro.democrud.dto.MasterKaryawanDto;
import com.mepro.democrud.types.ActiveStatus;
import com.mepro.democrud.util.Constants;
import com.mepro.democrud.util.DateRangeUtil;
import com.mepro.democrud.util.TransactionUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class MasterKaryawanDao {
    private static final Logger logger = LoggerFactory.getLogger(DepartemenDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<MasterKaryawanDto> getListMasterKaryawan(Long nik, String tglMulaiKerja, Long idDepartemen, Long subdepId, String namaLengkap) throws Exception {
        List<MasterKaryawanDto> listMasterKaryawan = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT tb1.nik nik, tb1.namalengkap namalengkap, TO_CHAR(tb1.tglmulaikerja, 'DD-MM-YYYY') tglmulaikerja, "
                + "tb2.namadepartemen namadepartemen, tb3.subdep_name subdep_name, tb1.idmasterkaryawan idmasterkaryawan  "
                + "FROM "
                + " master_karyawan tb1 "
                + "     LEFT JOIN departemen tb2 ON tb1.iddepartemen = tb2.iddepartemen "
                + "     LEFT JOIN subdepartemen tb3 ON tb1.subdep_id = tb3.subdep_id "
                + "WHERE "
                + " tb1.status = :status AND "
                + " tb1.subdep_id != 0 ";
        if (nik != null) {
            sql += "AND tb1.nik = "+nik+" ";
        }
        if (namaLengkap != null) {
            sql += "AND LOWER(tb1.namalengkap) LIKE '%"+namaLengkap.toLowerCase()+"%' ";
        }
        if (idDepartemen != null) {
            if (idDepartemen != -1L) {
                sql += "AND tb1.iddepartemen = "+idDepartemen+" ";
            }
        }
        if (subdepId != null) {
            if (subdepId != -1L) {
                sql += "AND tb1.subdep_id = "+subdepId+" ";
            }
        }
        if (tglMulaiKerja != null) {
            String[] dates = DateRangeUtil.splitDateRange(tglMulaiKerja);
            String startDate = dates[0];
            String endDate = dates[1];
            logger.info("startDate : "+startDate);
            logger.info("enddate : "+endDate);
            sql += "AND (tglmulaikerja >= TO_DATE('"+startDate+"', '"+Constants.ORACLE_DATE_FORMAT+"') AND "
                    + "tglmulaikerja <= TO_DATE('"+endDate+"', '"+Constants.ORACLE_DATE_FORMAT+"')) ";
        }
        
        String sqlOrderBy = "ORDER BY tb1.namalengkap ";
        
        NativeQuery<Object[]> query = session.createNativeQuery(sql + sqlOrderBy, Object[].class);
        query.addScalar("nik", StandardBasicTypes.LONG);
        query.addScalar("namalengkap", StandardBasicTypes.STRING);
        query.addScalar("tglmulaikerja", StandardBasicTypes.STRING);
        query.addScalar("namadepartemen", StandardBasicTypes.STRING);
        query.addScalar("subdep_name", StandardBasicTypes.STRING);
        query.addScalar("idmasterkaryawan", StandardBasicTypes.LONG);
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        List<Object[]> temp = query.list();
        if (!temp.isEmpty()) {
            for (Object[] row : temp) {
                Long rNik = (Long) row[0];
                String rNamaLengkap = (String) row[1];
                String rTglMulaiKerja = (String) row[2];
                String rDepartemen = (String) row[3];
                String rSubDepartemen = (String) row[4];
                Long rIdMasterKaryawan = (Long) row[5];
                
                MasterKaryawanDto data = new MasterKaryawanDto();
                data.setNik(rNik);
                data.setNamaLengkap(rNamaLengkap);
                data.setTglMulaiKerja(rTglMulaiKerja);
                data.setNamaDepartemen(rDepartemen);
                data.setSubdepName(rSubDepartemen);
                data.setIdMasterKaryawan(rIdMasterKaryawan);
                listMasterKaryawan.add(data);
            }
        }
        
        return listMasterKaryawan;
    }
}
