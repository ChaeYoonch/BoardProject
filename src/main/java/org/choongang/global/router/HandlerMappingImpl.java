package org.choongang.global.router;

import jakarta.servlet.http.HttpServletRequest;
import org.choongang.global.config.annotations.*;
import org.choongang.global.config.containers.BeanContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HandlerMappingImpl implements HandlerMapping{


    private String controllerUrl;

    @Override
    public List<Object> search(HttpServletRequest request) {
           // 반환형 data = List<Object>
        List<Object> items = getControllers(); // controller 담고 있음

        for (Object item : items) {
            /** Type 애노테이션에서 체크 S */ // GetMapping = 메서드용 | 클래스에 애노테이션이 달려 있는 경우
            // @RequestMapping, @GetMapping, @PostMapping, @PatchMapping, @PutMapping, @DeleteMapping
            if (isMatch(request,item.getClass().getDeclaredAnnotations(), false, null)) {
                // 메서드 체크 | 매개변수 3개 | isMatch : 값이 같은지 확인
                for (Method m : item.getClass().getDeclaredMethods()) { // getClass - 클래스 | bean 에서 List 형태로 꺼냄 = request 와 url 가 같은지 확인하는 것
                    if (isMatch(request, m.getDeclaredAnnotations(), true, controllerUrl)) {
                        return List.of(item, m); // 애노테이션 획득 -> url 형식으로 변경
                    }
                }
            }
            /** Type 애노테이션에서 체크 E */

            /**
             * Method 애노테이션에서 체크 S
             *  - Type 애노테이션 주소 매핑이 되지 않은 경우, 메서드에서 패턴 체크
             */
            for (Method m : item.getClass().getDeclaredMethods()) {
                if (isMatch(request, m.getDeclaredAnnotations(), true, null)) {
                    return List.of(item, m); // 클래스의 애노테이션 없을 때, 객체 & 메서드를 리스트 형식으로 컨트롤러 객체로 가져옴
                }
            }
            /* Method 애노테이션에서 체크 E */
        }

        return null;
    }


    /**
     * @param request
     * @param annotations : 적용 애노테이션 목록
     * @param isMethod : 메서드의 에노테이션 체크인지
     * @param prefixUrl : 컨트롤러 체크인 경우 타입 애노테이션에서 적용된 경우
     * @return
     */
    private boolean isMatch(HttpServletRequest request, Annotation[] annotations, boolean isMethod, String prefixUrl) {

        String uri = request.getRequestURI(); // 사용자 요청 주소 이거랑 MemberController 의 주소와 맞는지 확인
        String method = request.getMethod().toUpperCase();
        String[] mappings = null;
        for (Annotation anno : annotations) {

            if (anno instanceof RequestMapping) { // 모든 요청 방식 매핑
                RequestMapping mapping = (RequestMapping) anno;
                mappings = mapping.value();
            } else if (anno instanceof GetMapping && method.equals("GET")) { // GET 방식 매핑
                GetMapping mapping = (GetMapping) anno;
                mappings = mapping.value();
            } else if (anno instanceof PostMapping && method.equals("POST")) {
                PostMapping mapping = (PostMapping) anno;
                mappings = mapping.value();
            } else if (anno instanceof PutMapping && method.equals("PUT")) {
                PutMapping mapping = (PutMapping) anno;
                mappings = mapping.value();
            } else if (anno instanceof PatchMapping && method.equals("PATCH")) {
                PatchMapping mapping = (PatchMapping) anno;
                mappings = mapping.value();
            } else if (anno instanceof DeleteMapping && method.equals("DELETE")) {
                DeleteMapping mapping = (DeleteMapping) anno;
                mappings = mapping.value();
            } // 각자의 방법에 따라 매핑에 (member, join) 이 담김 -> /member/join 형식으로 바뀜

            if (mappings != null && mappings.length > 0) {

                String matchUrl = null;
                if (isMethod) {
                    String addUrl = prefixUrl == null ? "" : prefixUrl;
                    // 메서드인 경우 *와 {경로변수} 고려하여 처리
                    for(String mapping : mappings) {
                        String pattern = mapping.replace("/*", "/\\w*")
                                .replaceAll("/\\{\\w+\\}", "/(\\\\w*)");

                        Pattern p = Pattern.compile("^" + request.getContextPath() + addUrl + pattern + "$");
                        Matcher matcher = p.matcher(uri);
                        return matcher.find();
                    }
                } else {
                    List<String> matches = Arrays.stream(mappings)
                            .filter(s -> uri.startsWith(request.getContextPath() + s)).toList();
                    if (!matches.isEmpty()) {
                        matchUrl = matches.get(0);
                        controllerUrl = matchUrl;
                    }
                }
                return matchUrl != null && !matchUrl.isBlank();
            }
        }

        return false;
    }

    /**
     * 모든 컨트롤러 조회
     * @return
     */
    private List<Object> getControllers() {
       return BeanContainer.getInstance().getBeans().entrySet()
                    .stream()
                    .map(s -> s.getValue())
                .filter(b -> Arrays.stream(b.getClass().getDeclaredAnnotations()).anyMatch(a -> a instanceof Controller || a instanceof RestController))
                .toList();
    }
}
