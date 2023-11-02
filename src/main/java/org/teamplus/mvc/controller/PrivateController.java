package org.teamplus.mvc.controller;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.PrivateTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.ProjectService;
import org.teamplus.mvc.util.PageRequestDTO;
import org.teamplus.mvc.util.PageResponseDTO;

import java.util.HashMap;
import java.util.List;
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

        Map<String, Object> map = new HashMap<>();

        for(int i=0;i<5;i++){
            map.put("status",i);
            map.put("userNo",user.getUserNo());
            model.addAttribute("list"+i,service.selectPrivateList(map));
            model.addAttribute("count"+i,service.statusCount(map));

        }
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
    public String tasklistView(@SessionAttribute("user") UsersDTO user, PageRequestDTO pageRequestDTO, Model model) {
        pageRequestDTO.setUserNo(user.getUserNo());
        PageResponseDTO responseDTO = service.listWithSearchByUserNo(pageRequestDTO);
        List<MyNoteDTO> list = service.UsergetPageList(pageRequestDTO);
        model.addAttribute("list",list);
        model.addAttribute("paging",responseDTO);
        model.addAttribute("page",pageRequestDTO.getPage());
        model.addAttribute("userNo",user.getUserNo());

        return "dashboard/tasks-list-view";
    }

    // 메모 등록 화면
    @GetMapping("/taskwrite")
    public String taskwriteView() {
        return "dashboard/tasks-list-details";
    }
    @PostMapping("/save")
    public String taskwriteAction(@SessionAttribute("user") UsersDTO user, MyNoteDTO vo){
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

    //개인 캘린더
    @GetMapping("/privateCalendar")
    public String privateCalendar(@SessionAttribute("user") UsersDTO user) {

        return "/dashboard/privateCalendar";
    }


}
