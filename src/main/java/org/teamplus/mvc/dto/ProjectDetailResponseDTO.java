package org.teamplus.mvc.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDetailResponseDTO {
    private ProjectDTO prj;
    private List<TeamTodoDTO> todos;
    private List<UsersDTO> users;
}
