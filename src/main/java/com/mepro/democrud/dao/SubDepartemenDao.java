package com.mepro.democrud.dao;

import com.mepro.democrud.dto.SubDepartemenDto;
import com.mepro.democrud.types.ActiveStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class SubDepartemenDao implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DepartemenDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<SubDepartemenDto> getListSubDepartemen(Long subdepId, Long idDepartemen, String subdepName) throws Exception {
        List<SubDepartemenDto> listSubDepartemen = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT tb1.subdep_id subdepid, tb1.subdep_name subdepname, "
                + "tb2.iddepartemen iddepartemen, tb2.namadepartemen namadepartemen "
                + "FROM subdepartemen tb1 "
                + " JOIN departemen tb2 ON tb1.iddepartemen = tb2.iddepartemen "
                + "WHERE "
                + " tb1.status = :status AND "
                + " tb2.status = :status ";
        if (subdepId != null) {
            sql += "AND tb1.subdep_id = "+subdepId+" ";
        }
        if (idDepartemen != null) {
            sql += "AND tb2.iddepartemen = "+idDepartemen+" ";
        }
        if (subdepName != null) {
            sql += "AND LOWER(tb1.subdep_name) LIKE '%"+subdepName.toLowerCase()+"%' ";
        }
        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);
        query.addScalar("subdepid", StandardBasicTypes.LONG);
        query.addScalar("subdepname", StandardBasicTypes.STRING);
        query.addScalar("iddepartemen", StandardBasicTypes.LONG);
        query.addScalar("namadepartemen", StandardBasicTypes.STRING);
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        List<Object[]> temp = query.list();
        if (!temp.isEmpty()) {
            for (Object[] row : temp) {
                Long rSubdepId = (Long) row[0];
                String rSubdepName = (String) row[1];
                Long rIdDepartemen = (Long) row[2];
                String rNamaDepartemen = (String) row[3];
                
                SubDepartemenDto data = new SubDepartemenDto();
                data.setSubdepId(rSubdepId);
                data.setSubdepName(rSubdepName);
                data.setIdDepartemen(rIdDepartemen);
                data.setNamaDepartemen(rNamaDepartemen);
                listSubDepartemen.add(data);
            }
        }
        return listSubDepartemen;
    }
}
