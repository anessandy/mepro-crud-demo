package com.mepro.democrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    public UserInfoDto(Long nik, String namaLengkap) {
        this.nik = nik;
        this.namaLengkap = namaLengkap;
    }
    
    private Long nik;
    private String namaLengkap;
    private String userId;
    private Boolean isValid;
    private String password;
}
