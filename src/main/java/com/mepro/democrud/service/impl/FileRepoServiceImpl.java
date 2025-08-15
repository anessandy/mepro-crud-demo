package com.mepro.democrud.service.impl;

import com.mepro.democrud.dao.FileRepoDao;
import com.mepro.democrud.dto.FileRepoDto;
import com.mepro.democrud.entity.FileRepo;
import com.mepro.democrud.repository.FileRepoRepository;
import com.mepro.democrud.service.FileRepoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileRepoServiceImpl implements FileRepoService {
    private static final Logger logger = LoggerFactory.getLogger(SubDepartemenServiceImpl.class);
    
    @Autowired
    private FileRepoRepository fileRepoRepository;
    
    @Autowired
    private FileRepoDao fileRepoDao;
    
    @Override
    public Optional<FileRepo> findById(Long id) {
        return fileRepoRepository.findById(id);
    }
    
    @Override
    public List<FileRepoDto> getListFileRepo() {
        List<FileRepoDto> listFileRepo = new ArrayList<>();
        try {
            listFileRepo = fileRepoDao.getListFileRepo();
        }
        catch (Exception e) {
            logger.error("error on : " + e.getMessage());
        }
        return listFileRepo;
    }
    
    @Override
    public FileRepo save(FileRepo fileRepo) {
        return fileRepoRepository.save(fileRepo);
    }
    
    @Override
    public Long saveAttachmentReturnById(FileRepo fileRepo) {
        FileRepo saved = fileRepoRepository.save(fileRepo);
        return saved.getIdFileRepo(); // otomatis terisi setelah save
    }
}
