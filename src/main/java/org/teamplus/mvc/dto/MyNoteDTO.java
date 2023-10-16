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
public class MyNoteDTO {
    private String userNo;
    private int noteNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate noteDate;
    private String title;
    private String content;
}
