package org.teamplus.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataShareDTO {
    private int r;
    private int shareNo;
    private String projectNo;
    private String userNo;
    private String title;
    private String content;
    private String dataURL;
    private int count;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    //테이블 컬럼에는 없고, 파일업로드에 순수하게 DTO 용도로 사용합니다.
    private MultipartFile file;
}
