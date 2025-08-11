package com.mepro.democrud.dao;

import com.mepro.democrud.dto.DepartemenDto;
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
public class DepartemenDao implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DepartemenDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<DepartemenDto> getListDepartemen(Long idDepartemen, String codeDept, String namaDepartemen) throws Exception {
        List<DepartemenDto> listDepartemen = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT tb1.iddepartemen iddepartemen, tb1.namadepartemen namadepartemen, "
                + "tb1.codedept codedept, tb1.status status "
                + "FROM departemen tb1 "
                + "WHERE status = :status ";
        if (idDepartemen != null) {
            sql += "AND tb1.iddepartemen = "+idDepartemen+" ";
        }
        if (codeDept != null) {
            sql += "AND LOWER(tb1.codedept) = '"+codeDept.toLowerCase()+"' ";
        }
        if (namaDepartemen != null) {
            sql += "AND LOWER(tb1.namadepartemen) LIKE '%"+namaDepartemen.toLowerCase()+"%' ";
        }
        
        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);
        query.addScalar("iddepartemen", StandardBasicTypes.LONG);
        query.addScalar("namadepartemen", StandardBasicTypes.STRING);
        query.addScalar("codedept", StandardBasicTypes.STRING);
        query.addScalar("status", StandardBasicTypes.STRING);
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        List<Object[]> temp = query.list();
        if (!temp.isEmpty()) {
            for (Object[] row : temp) {
                Long rIdDepartemen = (Long) row[0];
                String rNamaDepartemen = (String) row[1];
                String rCodeDept = (String) row[2];
                String rStatus = (String) row[3];
                
                DepartemenDto data = new DepartemenDto();
                data.setIdDepartemen(rIdDepartemen);
                data.setNamaDepartemen(rNamaDepartemen);
                data.setCodeDept(rCodeDept);
                data.setStatus(rStatus);
                listDepartemen.add(data);
            }
        }
        return listDepartemen;
    }
}
