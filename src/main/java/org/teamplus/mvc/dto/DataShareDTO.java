package org.teamplus.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataShareDTO {
    private String shareNo;
    private String projectNo;
    private String userNo;
    private String content;
    private String dataURL;
    private int count;
}
