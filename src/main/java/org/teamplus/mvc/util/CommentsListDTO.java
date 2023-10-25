package org.teamplus.mvc.util;

import lombok.*;
import org.teamplus.mvc.dto.CommentsDTO;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentsListDTO {
    private List<CommentsDTO> commentsList;
    private Map<String,Object> subCommentsMap;
}
