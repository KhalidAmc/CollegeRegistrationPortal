<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="pageTitle" value="Dashboard" />
        <%@ include file="layout-header.jspf" %>

            <c:choose>

                <%-- ADMIN DASHBOARD --%>
                    <c:when test="${sessionScope.currentUser.role == 'ADMIN'}">

                        <section class="page-heading">
                            <div>
                                <p class="eyebrow">Admin Dashboard</p>
                                <h1>Hello, ${sessionScope.currentUser.fullName}</h1>
                                <p class="muted">
                                    Manage course offerings, monitor enrollment capacity, and maintain the course
                                    catalog.
                                </p>
                            </div>
                            <a class="btn" href="${pageContext.request.contextPath}/admin/courses">Manage courses</a>
                        </section>

                        <section class="stats-grid">
                            <div class="card stat">
                                <span>${totalCourses}</span>
                                <p>Active courses</p>
                            </div>

                            <div class="card stat">
                                <span>${sessionScope.currentUser.role}</span>
                                <p>Account role</p>
                            </div>


                        </section>

                        <section class="card">
                            <h2>Admin actions</h2>
                            <p class="muted">
                                Use the course management page to add new courses, review available seats,
                                and deactivate outdated course offerings.
                            </p>

                            <div class="action-row">
                                <a class="btn" href="${pageContext.request.contextPath}/admin/courses">Open course
                                    management</a>
                            </div>
                        </section>

                    </c:when>

                    <%-- STUDENT DASHBOARD --%>
                        <c:otherwise>

                            <section class="page-heading">
                                <div>
                                    <p class="eyebrow">Dashboard</p>
                                    <h1>Hello, ${sessionScope.currentUser.fullName}</h1>
                                    <p class="muted">Track your registered courses and continue building your schedule.
                                    </p>
                                </div>
                                <a class="btn" href="${pageContext.request.contextPath}/courses">Browse courses</a>
                            </section>

                            <section class="stats-grid">
                                <div class="card stat">
                                    <span>${registeredCourses.size()}</span>
                                    <p>Registered courses</p>
                                </div>

                                <div class="card stat">
                                    <span>${totalCourses}</span>
                                    <p>Available courses</p>
                                </div>

                                <div class="card stat">
                                    <span>${sessionScope.currentUser.role}</span>
                                    <p>Account role</p>
                                </div>
                            </section>

                            <section class="card">
                                <h2>My schedule</h2>

                                <c:choose>
                                    <c:when test="${empty registeredCourses}">
                                        <p class="empty-state">You are not registered in any courses yet.</p>
                                    </c:when>

                                    <c:otherwise>
                                        <div class="course-list">
                                            <c:forEach var="course" items="${registeredCourses}">
                                                <article class="course-row">
                                                    <div>
                                                        <strong>${course.code} — ${course.title}</strong>
                                                        <p>${course.term} · ${course.credits} credits</p>
                                                    </div>

                                                    <form method="post"
                                                        action="${pageContext.request.contextPath}/drop">
                                                        <input type="hidden" name="courseId" value="${course.id}">
                                                        <button class="btn btn-danger btn-small"
                                                            type="submit">Drop</button>
                                                    </form>
                                                </article>
                                            </c:forEach>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </section>

                        </c:otherwise>

            </c:choose>

            <%@ include file="layout-footer.jspf" %>