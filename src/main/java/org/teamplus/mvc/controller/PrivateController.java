package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.ProjectService;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("private")
public class PrivateController {

    private final ProjectService service;

    // todo read - insert,update,delete,list
    @GetMapping("/privatetodolist")
    public String taskboardView() {
        return "dashboard/todo-read";
    }

    // 메모 목록 화면
    @GetMapping("/tasklist")
    public String tasklistView(@SessionAttribute("user") UsersDTO user, Model model, MyNoteDTO vo) {
        model.addAttribute("list",service.selectList(user.getUserNo()));

        return "dashboard/tasks-list-view";
    }

    // 메모 등록 화면
    @GetMapping("/taskwrite")
    public String taskwriteView(Model model) {

        model.addAttribute("noteNo",service.getMynoteSequence());
        return "dashboard/tasks-list-details";
    }
    @PostMapping("/save")
    public String taskwriteAction(MyNoteDTO vo){
        int result = service.write(vo);
        return "redirect:/project/tasklist";
    }

    //메모 read 화면
    @GetMapping("/taskread")
    public String taskread(@RequestParam("noteNo") int noteno, Model model){
        MyNoteDTO vo = service.selectOne(noteno);
        model.addAttribute("vo",vo);

        return "dashboard/tasks-read";}

    //메모 수정 화면
    @GetMapping("/taskmodify")
    public String tasksmodify(@RequestParam("noteNo")int noteno, Model model){
        MyNoteDTO vo = service.selectOne(noteno);
        model.addAttribute("vo",vo);

        return "dashboard/tasks-modify";}

    @PostMapping("/modifysave")
    public String modifysave(MyNoteDTO vo){
        service.update(vo);
        return "redirect:/project/taskread?noteNo="+vo.getNoteNo();
    }

    @PostMapping("/taskdelete")
    public String taskdelete(@RequestParam(name = "noteNo", required = false) int noteno) {
        log.info(">>>>>>>>>>>>> noteNo :{} ",noteno);
        service.delete(noteno);
        return "redirect:/project/tasklist";
    }


}
