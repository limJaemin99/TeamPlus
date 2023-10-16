package org.teamplus.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDTO {
    private String userNo;
    private String projectNo;
    private int leader;
}
