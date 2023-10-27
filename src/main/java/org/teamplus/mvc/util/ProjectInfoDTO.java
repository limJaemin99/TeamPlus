package org.teamplus.mvc.util;

import lombok.*;
import org.teamplus.mvc.dto.ProjectDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectInfoDTO {
    private ProjectDTO projectDTO;
    private List<TeamTodoDTO> todoList;
    private List<UsersDTO> usersList;
}
