package org.teamplus.mvc.util;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailCodeDTO {  //메일 코드 전용 DTO 입니다. 10.19 오후 (재민)
    private String first;
    private String second;
    private String third;
    private String fourth;
}
