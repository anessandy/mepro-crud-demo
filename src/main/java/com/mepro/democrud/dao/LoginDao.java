package com.mepro.democrud.dao;

import com.mepro.democrud.dto.UserInfoDto;
import com.mepro.democrud.types.ActiveStatus;
import com.mepro.democrud.util.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LoginDao {
    private static final Logger logger = LoggerFactory.getLogger(LoginDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<UserInfoDto> getListUserInfo(String userId, String password) throws Exception {
        List<UserInfoDto> listUserInfo = new ArrayList<>();
        Boolean isValid = false;
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT tb1.nik nik, tb1.namalengkap namalengkap, "
                + "tb2.userid userid, tb2.password password, tb2.deskey "
                + "FROM master_karyawan tb1 "
                + " LEFT JOIN users tb2 ON tb1.nik = tb2.nik "
                + "WHERE "
                + " tb1.status = :status AND "
                + " tb2.userid = :userId AND "
                + " tb2.status = :status ";
        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);
        query.addScalar("userid", StandardBasicTypes.STRING);
        query.addScalar("deskey", StandardBasicTypes.STRING);
        query.addScalar("password", StandardBasicTypes.STRING);
        query.addScalar("nik", StandardBasicTypes.LONG);
        query.addScalar("namalengkap", StandardBasicTypes.STRING);

        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        query.setParameter("userId", userId.toUpperCase());
        List<Object[]> temp = query.list();
        if (!temp.isEmpty()) {
            for (Object[] row : temp) {
                String rUserId = (String) row[0];
                String rDeskey = (String) row[1];
                String rPasswordStored = (String) row[2];
                Long rNik = (Long) row[3];
                String rNamaLengkap = (String) row[4];
                isValid = SecurityUtil.passwordCommonDecrypt(rPasswordStored,
                        password, rDeskey);
                if (isValid) {
                    UserInfoDto data = new UserInfoDto();
                    data.setNik(rNik);
                    data.setNamaLengkap(rNamaLengkap);
                    data.setUserId(rUserId);
                    data.setPassword(rPasswordStored);
                    listUserInfo.add(data);
                }
            }
        }
        return listUserInfo;
    }
}
