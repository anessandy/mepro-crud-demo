
package com.mepro.democrud.service;

import com.mepro.democrud.dto.FileRepoDto;
import com.mepro.democrud.entity.FileRepo;
import java.util.List;
import java.util.Optional;

public interface FileRepoService {
    List<FileRepoDto> getListFileRepo();
    Optional<FileRepo> findById(Long id);
    FileRepo save (FileRepo fileRepo);
    Long saveAttachmentReturnById(FileRepo fileRepo);
}
