package org.choongang.board.exceptions;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        super("등록된 게시글이 아닙니다.", HttpServletResponse.SC_NOT_FOUND);
    }
}