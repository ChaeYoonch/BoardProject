package org.choongang.global.router;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface HandlerMapping {
    List<Object> search(HttpServletRequest request);

} // Impl 이 HandlerMapping 구현한 것
