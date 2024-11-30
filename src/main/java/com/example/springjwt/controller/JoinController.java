package com.example.springjwt.controller;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {
    private final JoinService joinService;
    @PostMapping("/join")
    public String joinProcess(@ModelAttribute JoinDTO joinDTO){
        log.info("joinDTO = {}", joinDTO);
        joinService.joinProcess(joinDTO);
        return "ok";
    }
}
