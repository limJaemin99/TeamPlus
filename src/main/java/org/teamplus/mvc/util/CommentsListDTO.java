package org.teamplus.mvc.util;

import lombok.*;
import org.teamplus.mvc.dto.CommentsDTO;
import org.teamplus.mvc.dto.SubCommentsDTO;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentsListDTO {
    private List<CommentsDTO> commentsList;
    private List<SubCommentsDTO> subCommentsList;
    private List<UsersDTO> memberList;
    private List<UsersDTO> subMemberList;
}
