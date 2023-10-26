package org.teamplus.mvc.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.teamplus.mvc.dto.UsersDTO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class PageRequestDTO {

	private String projectNo;
	private String userNo;
	private int noteNo;
	private int status;

	//page,size 는 start,end 계산에 필요한 값
	private int page = 1;	//현재 페이지 번호
	private int size = 10;	//size 는 한 개 페이지 글 갯수

	//sql 쿼리에 필요한 값
	private int start;		//페이지 시작 글번호 (rownum)
	private int end;		//페이지 마지막 글번호 (rownum)

	//검색 파라미터
//	private String[] types;		//"twc" 를 동적 쿼리로 전달하기 위해 배열로 변환하여 저장 {"t","w","c"}
	private String type;		//View 에서 "twc" 로 전달되는 값 저장
	private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")	private LocalDate from;
	@DateTimeFormat(pattern = "yyyy-MM-dd")	private LocalDate to;

	//int page, int size, String[] types, String Keyword, LocalDate from, LocalDate to 는 list.html 에서 검색 필드로 전달해 줄 파라미터들
	//	list.html 에서 검색 버튼을 누르면 /community/list getmapping 이다. 해당 핸들러 메소드에 인자로 PageRequestDTO 를 선언한다.
	//									ㄴ 핸들러 메소드는 PageRequestDTO 로 모든 파라미터를 받는다. (생성자 + setter 동작)
																								//★기본생성자는 setter 가 꼭 필요함★
	public void setDatas(){	//오라클에서만 필요하다. mysql 은 쉽게 할 수 있는 limit 이라는 연산자가 있다.
		start = (page - 1) * size + 1;    //글목록 시작행번호 (rownum)
		end = start + (size - 1);

		//String "tc" 와 같이 View 에서 받은 파라미터값은 배열로 변경
//		if(type != null && !type.isEmpty() && !type.equals("a"))	//type = "a" 를 전체로 할 예정
//			types = type.split("");	//"tc" 를 new String[]{"t","c"} 로 변환한다.
	}

	//10.05
	//글 읽기,수정,삭제 등에 페이지 번호와 검색 파라미터를 URL에 계속 데리고다녀야 한다.
	//이를 위해서 문자열 생성 메소드를 정의한다.
	private String link;	//URL에 들어갈 파라미터 문자열

	//Getter 는 처리 코드가 필요하여 직접 작성한다.
	public String getLink(){

		if(link == null){
			StringBuilder builder = new StringBuilder();
			builder.append("page=" + this.page);
			if(this.type !=null && this.type.length()>0 && this.keyword != null) {
				builder.append("&type=" + this.type);
				try {
					builder.append("&keyword=" + URLEncoder.encode(this.keyword, "UTF-8"));
					//키워드는 한글 등 다국어문자일 경우 인코딩이 필요합니다.
				} catch (UnsupportedEncodingException e) {
				}
			}

			if(this.from !=null && this.to !=null){   //아직 UI 에는 미구현한 상태입니다.
				builder.append("&from=" + this.from);
				builder.append("&to=" + this.to);
			}

			this.link=builder.toString();
		}
		return this.link;
		//최종 link 예시 : page=3&type=tc&keyword=hi&from=2023-03-01&to=2023-03-31
	}



}
