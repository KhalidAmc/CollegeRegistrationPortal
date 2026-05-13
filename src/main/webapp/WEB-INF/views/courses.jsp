<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:set var="pageTitle" value="Courses" />
        <%@ include file="layout-header.jspf" %>

            <section class="page-heading">
                <div>
                    <p class="eyebrow">Course Catalog</p>
                    <h1>Find your next course</h1>
                </div>
            </section>

            <form method="get" action="${pageContext.request.contextPath}/courses" class="search-bar card">
                <input type="search" name="q" value="${q}" placeholder="Search by code, title, or keyword">
                <button class="btn" type="submit">Search</button>
            </form>

            <section class="catalog-grid">
                <c:forEach var="course" items="${courses}">
                    <c:set var="takenSeats" value="${seatCounts[course.id]}" />
                    <c:set var="isEnrolled" value="${enrolledCourseIds.contains(course.id)}" />
                    <article class="card course-card">
                        <div class="course-topline">
                            <span class="badge">${course.code}</span>
                            <span>${takenSeats}/${course.capacity} seats</span>
                        </div>
                        <h2>${course.title}</h2>
                        <p>${course.description}</p>
                        <p class="muted">${course.term} · ${course.credits} credits</p>
                        <c:choose>

                            <%-- Admin users should manage courses, not enroll in them --%>
                                <c:when test="${sessionScope.currentUser.role == 'ADMIN'}">
                                    <a class="btn btn-secondary"
                                        href="${pageContext.request.contextPath}/admin/courses">
                                        Manage
                                    </a>
                                </c:when>

                                <%-- Student is already enrolled --%>
                                    <c:when test="${isEnrolled}">
                                        <button class="btn btn-secondary" disabled>Already enrolled</button>
                                    </c:when>

                                    <%-- Course is full --%>
                                        <c:when test="${takenSeats >= course.capacity}">
                                            <button class="btn btn-secondary" disabled>Full</button>
                                        </c:when>

                                        <%-- Student can enroll --%>
                                            <c:otherwise>
                                                <form method="post" action="${pageContext.request.contextPath}/enroll">
                                                    <input type="hidden" name="courseId" value="${course.id}">
                                                    <button class="btn" type="submit">Enroll</button>
                                                </form>
                                            </c:otherwise>

                        </c:choose>
                    </article>
                </c:forEach>
            </section>

            <%@ include file="layout-footer.jspf" %>