package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamplus.mvc.service.ProjectService;
import org.teamplus.mvc.util.InquiryMail;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/modal")
public class ModalRestController {

    private final ProjectService service;

    @GetMapping("/inquiry/{email}/{content}")
    public Map<String,Integer> inquiry(@PathVariable String email, @PathVariable String content){
        String to = "teamplus0419@naver.com";
        Map<String,Integer> map = new HashMap<>();

        InquiryMail mail = new InquiryMail();
        mail.setEmail(email);
        mail.setContent(content);
        int result = mail.sendMail(to);

        map.put("result",result);

        return map;
    }
}
