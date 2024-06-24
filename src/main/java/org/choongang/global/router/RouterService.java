package org.choongang.global.router;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.global.config.annotations.Service;
import org.choongang.global.exceptions.ExceptionHandlerService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouterService {

    private final HandlerMappingImpl handlerMapping;
    private final HandlerAdapterImpl handlerAdapter;
    private final StaticResourceMappingImpl staticResourceMapping;
    private final ExceptionHandlerService exceptionHandlerService;

    /**
     * 컨트롤러 라우팅
     * 요청에 따라 연결하는 controller 찾아주는 역할 | HandlerMapping 이 찾아줌
     */
    public void route(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Object> data = null; // data = 객체를 담아두는 리스트 = Object 자료형의 변수
        try {
            data = handlerMapping.search(req); // 결과물이 객체 | req = 사용자가 입력한 url

            // 요청 주소
            String requestUrl = req.getRequestURI();
            requestUrl = req.getQueryString() == null || req.getQueryString().isBlank() ? requestUrl : requestUrl + "?" + req.getQueryString();
            req.setAttribute("requestUrl", requestUrl);

            // 요청 메서드
            req.setAttribute("method", req.getMethod());

            if (data == null) {

                /**
                 *  처리 가능한 컨트롤러를 못찾은 경우 지정된 정적 경로에 파일이 있는지 체크 하고
                 *  해당 자원을 파일로 읽어 온 후 파일에 맞는 Content-Type 으로 응답 헤더 추가 및 Body 쪽에 출력하여 보일 수 있도록 한다.
                 *  정적 경로에도 파일을 찾을 수 없다면 404 응답 코드를 내보낸다.
                 */

                // 정적 자원이라면 정적 라우팅 처리
                if (staticResourceMapping.check(req)) {
                    staticResourceMapping.route(req, res);
                    return;
                }

                // 컨트롤러도 발견하지 못하고 정적 라우팅도 아니라면 404
                int status = HttpServletResponse.SC_NOT_FOUND;
                res.setStatus(status);

                // 없는 페이지 응답 상태 코드 추가
                req.setAttribute("status", status);

                // 404 없는 페이지 출력
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/templates/errors/404.jsp");
                rd.forward(req, res);
                return;
            }

            // 찾은 컨트롤러 요청 메서드를 실행 | 메서드에 함수가 1 대 1 대응 | (controller, 함수)
            handlerAdapter.execute(req, res, data); // 찾은 컨트롤러에 해당하는 메서드 실행 = 컨트롤러 기능 -> 응답 보내주려고
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.setAttribute("exception", e);
            if (e instanceof ServletException || e instanceof IOException) {
                int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                res.setStatus(status);
                req.setAttribute("status", status);
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/templates/errors/500.jsp");
                rd.forward(req, res);

                e.printStackTrace();
                return;
            }

            // 예외 페이지 처리
            if (data != null) {
                exceptionHandlerService.handle(e, data.get(0));
            }
        }
    }
}