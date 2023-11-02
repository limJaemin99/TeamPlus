package org.teamplus.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.UsersService;

@Slf4j
@RestController
@RequestMapping("/api")
public class SocialLoginController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/snsUpdate")
    public String snsUpdate(@RequestBody UsersDTO usersDTO, Model model) {


        try {
            log.info(usersDTO.toString());

            return "전송 성공";
        } catch (Exception e) {
            e.printStackTrace();
            return "오류 발생";
        }
    }
}