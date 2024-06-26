package org.choongang.global;

public interface Interceptor { // controller 실행 전에 실행하는 메서드
    boolean preHandle();
}