package org.teamplus.mvc.util;

import lombok.*;
import org.teamplus.mvc.dto.TeamDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchDTO {
    private List<TeamTodoDTO> teamTodoList;
    private List<UsersDTO> teamMemberList;
    private List<UsersDTO> usersList;
    private PageResponseDTO pageResponseDTO;
    private int page;
}
