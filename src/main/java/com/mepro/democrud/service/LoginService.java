package com.mepro.democrud.service;

import com.mepro.democrud.dto.UserInfoDto;
import com.mepro.democrud.repository.LoginRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired
    private LoginRepository loginRepository;
    
    public Optional<UserInfoDto> login(String userId, String password) throws Exception {
        List<UserInfoDto> results = loginRepository.getListUserInfo(userId, password);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
