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
public class MyNoteDTO {
    private String userNo;
    private int noteNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate noteDate;
    private String title;
    private String content;
    private String password;
    private String isPrivate;
}
