package com.mepro.democrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubDepartemenDto {
    private Long subdepId;
    private String subdepName;
    private Long idDepartemen;
    private String namaDepartemen; 
}
