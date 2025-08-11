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
public class DepartemenDto {
    private Long idDepartemen;
    private String namaDepartemen;
    private String codeDept;
    private String status;
}
