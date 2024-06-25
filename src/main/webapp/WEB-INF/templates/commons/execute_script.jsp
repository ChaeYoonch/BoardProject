<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${! empty script}"> <!-- script 실행할 수 있도록 넣어줌 -->
    <script>
        ${script}
    </script>
</c:if>