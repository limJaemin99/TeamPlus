package org.teamplus.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCommentsDTO {
    private int subCommentNo;
    private String userNo;
    private int commentNo;
    private String subContent;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;
}
