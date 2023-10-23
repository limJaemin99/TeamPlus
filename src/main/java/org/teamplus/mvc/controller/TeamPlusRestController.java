package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.PrivateTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.ProjectService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
public class TeamPlusRestController {

    private final ProjectService service;

    /* 개인 - 할일 삭제 기능 */
    @DeleteMapping("/private/privateDelete/{todoNo}")
    public Map<String, Integer> todoDelete(@PathVariable String todoNo){
        int count = service.delete(todoNo);
        Map<String,Integer> resultMap = new HashMap<>();
        resultMap.put("count",count);

        return resultMap;
    }

    /* 개인 - 할일 수정 기능 */
    @PatchMapping("/private/privateTodoUpdate/{todoNo}")
    public Map<String,Integer> todoUpdate(@RequestBody @Valid PrivateTodoDTO vo){
        Map<String,Integer> map = new HashMap<>();
        int count = service.update(vo);
        map.put("count",count);
        return map;
    }

    /* 개인 - 할일 한개 보기 */
    @GetMapping("/private/privateTodoSelectOne/{todoNo}")
    public PrivateTodoDTO selectOne(@PathVariable String todoNo){
        PrivateTodoDTO vo = service.selectPrivateOne(todoNo);

        return vo;
    }
}
