<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="pageTitle" value="Admin Courses" />
        <%@ include file="layout-header.jspf" %>

            <section class="page-heading">
                <div>
                    <p class="eyebrow">Admin</p>
                    <h1>Course management</h1>
                    <p class="muted">Add new courses and deactivate outdated courses.</p>
                </div>
            </section>

            <section class="grid-2">
                <form method="post" action="${pageContext.request.contextPath}/admin/courses" class="card form-stack">
                    <h2>Add course</h2>
                    <input type="hidden" name="action" value="create">
                    <label>Code <input name="code" required></label>
                    <label>Title <input name="title" required></label>
                    <label>Description <textarea name="description" required rows="4"
                            placeholder="Short course description"></textarea></label>
                    <label>Term <input name="term" required></label>
                    <div class="form-row">
                        <label>Credits <input type="number" name="credits" required min="1" value="3"></label>
                        <label>Capacity <input type="number" name="capacity" required min="1" value="30"></label>
                    </div>
                    <button class="btn" type="submit">Add course</button>
                </form>

                <div class="card">
                    <h2>Active courses</h2>
                    <div class="course-list compact">
                        <c:forEach var="course" items="${courses}">
                            <article class="course-row">
                                <div>
                                    <strong>${course.code}</strong>
                                    <p>${course.title} · ${seatCounts[course.id]}/${course.capacity} seats</p>
                                </div>
                                <form method="post" action="${pageContext.request.contextPath}/admin/courses">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="courseId" value="${course.id}">
                                    <button class="btn btn-danger btn-small" type="submit">Deactivate</button>
                                </form>
                            </article>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <%@ include file="layout-footer.jspf" %>