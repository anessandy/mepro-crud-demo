package com.mepro.democrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long nik;
    private String namaLengkap;
    private String userId;
    private Boolean isValid;
    private String password;
}
