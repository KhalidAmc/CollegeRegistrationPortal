<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="pageTitle" value="Register" />
        <%@ include file="layout-header.jspf" %>

            <section class="auth-card card narrow">
                <h1>Create student account</h1>
                <p class="muted">Register to browse and enroll in available courses.</p>
                <form method="post" action="${pageContext.request.contextPath}/register" class="form-stack">
                    <label>Full name
                        <input type="text" name="fullName" required minlength="3" placeholder="Your full name">
                    </label>
                    <label>Email
                        <input type="email" name="email" required>
                    </label>
                    <label>Password
                        <input type="password" name="password" required minlength="8"
                            placeholder="At least 8 characters">
                    </label>
                    <button class="btn" type="submit">Create account</button>
                </form>
            </section>

            <%@ include file="layout-footer.jspf" %>