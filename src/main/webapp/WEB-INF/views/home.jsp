<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="pageTitle" value="Home" />
        <%@ include file="layout-header.jspf" %>

            <section class="hero card">
                <div>
                    <h1>Manage your college course registration</h1>
                    <p class="hero-text">Students can browse courses, register, drop courses, and track their schedule.
                        Admin users can manage course availability and capacity.</p>
                    <div class="hero-actions">
                        <a class="btn" href="${pageContext.request.contextPath}/login">Try demo login</a>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/register">Create student
                            account</a>
                    </div>
                </div>
                <div class="hero-panel">
                    <h2>Demo credentials</h2>
                    <p><strong>Student:</strong> student@algonquinlive.com</p>
                    <p><strong>Password:</strong> Student123!</p>
                    <hr>
                    <p><strong>Admin:</strong> admin@algonquinlive.com</p>
                    <p><strong>Password:</strong> Admin123!</p>
                </div>
            </section>

            <section class="feature-grid">
                <article class="card feature"><span>01</span>
                    <h3>Authentication</h3>
                    <p>Session-based login with BCrypt password hashing.</p>
                </article>
                <article class="card feature"><span>02</span>
                    <h3>Course Catalog</h3>
                    <p>Searchable courses with seat availability and enrollment actions.</p>
                </article>
                <article class="card feature"><span>03</span>
                    <h3>Admin Tools</h3>
                    <p>Add and deactivate courses through a protected admin dashboard.</p>
                </article>
            </section>

            <%@ include file="layout-footer.jspf" %>