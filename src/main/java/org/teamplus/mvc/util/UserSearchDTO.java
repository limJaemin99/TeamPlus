package org.teamplus.mvc.util;

import lombok.*;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSearchDTO {
    private List<MyNoteDTO> myNoteList;
    private List<UsersDTO> usersList;
    private PageResponseDTO pageResponseDTO;
    private int page;
}
