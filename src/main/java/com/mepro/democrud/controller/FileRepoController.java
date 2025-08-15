package com.mepro.democrud.controller;

import com.mepro.democrud.dto.FileRepoDto;
import com.mepro.democrud.entity.FileRepo;
import com.mepro.democrud.service.FileRepoService;
import com.mepro.democrud.types.ActiveStatus;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/filerepo")
public class FileRepoController implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(FileRepoController.class);
    
    @Autowired
    private FileRepoService fileRepoService;
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @GetMapping("/index")
    public String index(Model model) {
        logger.info("index file repo");
        List<FileRepoDto> list = fileRepoService.getListFileRepo();
        model.addAttribute("listFileRepo", list);
        return "filerepo/index";
    }
    
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Silakan pilih file!");
            return "filerepo/index";
        }
        
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            
            String originFileName = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originFileName);
            
            FileRepo data = new FileRepo();
            data.setFileName(file.getOriginalFilename());
            data.setFileExt(ext);
            data.setStatus(ActiveStatus.ACTIVE.getCode());
            Long idAttachment = fileRepoService.saveAttachmentReturnById(data);
            String newFileName = idAttachment + "." + ext;
            
            Path filePath = path.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            
            model.addAttribute("message", "Upload berhasil: " + file.getOriginalFilename());
        } 
        catch (Exception e) {
            logger.error("upload : "+e.getMessage());
        }
        return "redirect:/filerepo/index"; 
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        logger.info("download file with id : "+id);
        FileRepo fileData = fileRepoService.findById(id)
                .orElseThrow(() -> new RuntimeException("File tidak ditemukan"));
        Path filePath = Paths.get(uploadDir).resolve(fileData.getIdFileRepo() + "." +fileData.getFileExt()).normalize();
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new InputStreamResource(Files.newInputStream(filePath));
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, 
                    "attachment; filename=\"" + fileData.getFileName()+ "\"")
            .body(resource);
    }
}
