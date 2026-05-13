<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="pageTitle" value="Login" />
        <%@ include file="layout-header.jspf" %>

            <section class="auth-card card narrow">
                <h1>Welcome back</h1>
                <p class="muted">Log in as a student or administrator.</p>
                <form method="post" action="${pageContext.request.contextPath}/login" class="form-stack">
                    <label>Email
                        <input type="email" name="email" required>
                    </label>
                    <label>Password
                        <input type="password" name="password" required>
                    </label>
                    <button class="btn" type="submit">Login</button>
                </form>
                <p class="small-text">No account? <a href="${pageContext.request.contextPath}/register">Create a student
                        account</a>.</p>
            </section>

            <%@ include file="layout-footer.jspf" %>