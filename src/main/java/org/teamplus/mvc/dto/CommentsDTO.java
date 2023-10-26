package org.teamplus.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentsDTO {
    private int commentNo;
    private String todoNo;
    private String userNo;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;
}
