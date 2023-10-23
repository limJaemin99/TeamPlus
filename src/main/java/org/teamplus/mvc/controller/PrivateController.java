package org.teamplus.mvc.controller;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.PrivateTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.ProjectService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("private")
public class PrivateController {

    private final ProjectService service;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    // todo read - insert,update,delete,list
    @GetMapping("/privatetodolist")
    public String taskboardView(@SessionAttribute("user") UsersDTO user, Model model) {

//        model.addAttribute("list",service.selectPrivateList(user.getUserNo()));

        PrivateTodoDTO vo = new PrivateTodoDTO();
        
        //status 3에 대한 카운트
        vo.setStatus(3);
        Map<String, Object> map = new HashMap<>();
        map.put("status",vo.getStatus());
        map.put("userNo",user.getUserNo());
        model.addAttribute("list3",service.selectPrivateList(map));
        model.addAttribute("count3",service.statusCount(map));

        //status 0에 대한 카운트
        vo.setStatus(0);
        map.put("status",vo.getStatus());
        map.put("userNo",user.getUserNo());
        model.addAttribute("list0",service.selectPrivateList(map));
        model.addAttribute("count0",service.statusCount(map));

        //status 2에 대한 카운트
        vo.setStatus(2);
        map.put("status",vo.getStatus());
        map.put("userNo",user.getUserNo());
        model.addAttribute("list2",service.selectPrivateList(map));
        model.addAttribute("count2",service.statusCount(map));

        //status 1에 대한 카운트
        vo.setStatus(1);
        map.put("status",vo.getStatus());
        map.put("userNo",user.getUserNo());
        model.addAttribute("list1",service.selectPrivateList(map));
        model.addAttribute("count1",service.statusCount(map));

        return "dashboard/todo-read";
    }

    @PostMapping("/privateTodoWrite")
    public String todoWrite(@SessionAttribute("user") UsersDTO user,PrivateTodoDTO vo){
        String todoNo = user.getUserNo()+service.getPrivateSequence();
        vo.setTodoNo(todoNo);
        service.write(vo);

        return "redirect:/private/privatetodolist";
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // 메모 목록 화면
    @GetMapping("/tasklist")
    public String tasklistView(@SessionAttribute("user") UsersDTO user, Model model, MyNoteDTO vo) {
        model.addAttribute("list",service.selectList(user.getUserNo()));

        return "dashboard/tasks-list-view";
    }

    // 메모 등록 화면
    @GetMapping("/taskwrite")
    public String taskwriteView() {
        return "dashboard/tasks-list-details";
    }
    @PostMapping("/save")
    public String taskwriteAction(@SessionAttribute("user") UsersDTO user,MyNoteDTO vo){
        int noteNo = service.getMynoteSequence()+1;
        vo.setNoteNo(noteNo);
        vo.setUserNo(user.getUserNo());
        service.write(vo);
        return "redirect:/private/tasklist";
    }

    //메모 read 화면
    @GetMapping("/taskread")
    public String taskread(@RequestParam("noteNo") int noteno, Model model){
        MyNoteDTO vo = service.selectOne(noteno);
        model.addAttribute("vo",vo);

        return "dashboard/tasks-read";}

    //메모 수정 화면
    @GetMapping("/taskmodify")
    public String tasksmodify(@SessionAttribute("user") UsersDTO user,@RequestParam("noteNo")int noteno, Model model){
        MyNoteDTO vo = service.selectOne(noteno);
        model.addAttribute("vo",vo);

        return "dashboard/tasks-modify";}

    @PostMapping("/modifysave")
    public String modifysave(MyNoteDTO vo){
        service.update(vo);
        return "redirect:/private/taskread?noteNo="+vo.getNoteNo();
    }

    @PostMapping("/taskdelete")
    public String taskdelete(@RequestParam(name = "noteNo", required = false) int noteno) {
        log.info(">>>>>>>>>>>>> noteNo :{} ",noteno);
        service.delete(noteno);
        return "redirect:/private/tasklist";
    }


}
