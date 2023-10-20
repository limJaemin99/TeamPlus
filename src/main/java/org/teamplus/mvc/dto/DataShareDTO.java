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
public class DataShareDTO {
    private String shareNo;
    private String projectNo;
    private String userNo;
    private String title;
    private String content;
    private String dataURL;
    private int count;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;
}
