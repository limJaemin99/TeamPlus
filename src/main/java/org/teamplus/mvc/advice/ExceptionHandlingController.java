package org.teamplus.mvc.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Log4j2
@Controller
public class ExceptionHandlingController implements ErrorController {

    // 에러 페이지 정의
    private final String ERROR_404_PAGE_PATH = "ui/404Errors";
    private final String ERROR_500_PAGE_PATH = "ui/500Errors";
    private final String ERROR_ETC_PAGE_PATH = "ui/Errors";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {

        // 에러 코드를 획득
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // 에러 코드에 대한 상태 정보
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

        if (status != null) {
            // HttpStatus와 비교해 페이지 분기를 나누기 위한 변수
            int statusCode = Integer.valueOf(status.toString());

            // 로그로 상태값을 기록 및 출력
            log.info("httpStatus : " + statusCode);

            // ● 404 error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // 에러 페이지에 표시할 정보
                model.addAttribute("code", status.toString());
                model.addAttribute("message", httpStatus.getReasonPhrase());
                model.addAttribute("timestamp", new Date());

                return ERROR_404_PAGE_PATH;
            }

            // ● 500 error
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                //이전 경로 보냄
                String referer = request.getHeader("REFERER");
                model.addAttribute("pagePath", referer);

                return ERROR_500_PAGE_PATH;
            }
        }

        // ● 정의한 에러 외 모든 에러는 ui/error 페이지로 보냄
        return ERROR_ETC_PAGE_PATH;
    }

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }

}