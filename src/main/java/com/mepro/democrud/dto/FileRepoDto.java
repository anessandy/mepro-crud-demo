package com.mepro.democrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRepoDto {
    private Long idFileRepo;
    private String fileName;
    private String fileExt;
    private String status;
}
