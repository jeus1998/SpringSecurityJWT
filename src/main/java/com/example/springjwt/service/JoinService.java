package com.example.springjwt.service;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public void joinProcess(JoinDTO joinDTO){
        if(userRepository.existsByUsername(joinDTO.getUsername())) return;

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(joinDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        userEntity.setRole("ROLE_ADMIN");

        userRepository.save(userEntity);
    }
}
