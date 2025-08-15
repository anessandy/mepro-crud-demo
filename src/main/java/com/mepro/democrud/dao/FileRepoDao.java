package com.mepro.democrud.dao;

import com.mepro.democrud.dto.FileRepoDto;
import com.mepro.democrud.types.ActiveStatus;
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

@Transactional
@Repository
public class FileRepoDao {
    private static final Logger logger = LoggerFactory.getLogger(FileRepoDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<FileRepoDto> getListFileRepo() throws Exception {
        List<FileRepoDto> listFileRepo = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT idfilerepo, filename, fileext, status "
                + "FROM filerepo WHERE status = :status ";
        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);
        query.addScalar("idfilerepo", StandardBasicTypes.LONG);
        query.addScalar("filename", StandardBasicTypes.STRING);
        query.addScalar("fileext", StandardBasicTypes.STRING);
        query.addScalar("status", StandardBasicTypes.STRING);
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        
        List<Object[]> temp = query.list();
        if (!temp.isEmpty()) {
            for (Object[] row : temp) {
                Long rIdFileRepo = (Long) row[0];
                String rFileName = (String) row[1];
                String rFileExt = (String) row[2];
                String rStatus = (String) row[3];
                
                FileRepoDto data = new FileRepoDto();
                data.setIdFileRepo(rIdFileRepo);
                data.setFileName(rFileName);
                data.setFileExt(rFileExt);
                data.setStatus(rStatus);
                listFileRepo.add(data);
            }
        }
        return listFileRepo;
    }
}
