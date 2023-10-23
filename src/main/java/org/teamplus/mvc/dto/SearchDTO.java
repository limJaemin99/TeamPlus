package org.teamplus.mvc.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchDTO {
    private List<TeamTodoDTO> teamTodoList;
    private List<TeamDTO> teamList;
    private List<UsersDTO> usersList;
}
