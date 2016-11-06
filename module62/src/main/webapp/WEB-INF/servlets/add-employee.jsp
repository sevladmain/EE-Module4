<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:url value="/employee/added" var="userActionUrl" />

<form:form class="form-horizontal" method="post"
           modelAttribute="employeeForm" action="${userActionUrl}">

    <form:hidden path="id" />

    <spring:bind path="firstName">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">Ім'я</span>
                <form:input path="firstName" type="text" class="form-control" aria-describedby="basic-addon1"
                            id="name" placeholder="Ім'я" />
        </div>
    </spring:bind>
    <spring:bind path="lastName">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">Прізвище</span>
            <form:input path="lastName" type="text" class="form-control" aria-describedby="basic-addon1"
                        id="lastName" placeholder="Прізвище" />
        </div>
    </spring:bind>
    <spring:bind path="dateBirth">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">Дата народження</span>
            <form:input path="dateBirth" type="text" class="form-control" aria-describedby="basic-addon1"
                        id="dateBirth" placeholder="Дата народження" />
        </div>
    </spring:bind>
    <spring:bind path="salary">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">Оклад</span>
            <form:input path="salary" type="text" class="form-control" aria-describedby="basic-addon1"
                        id="salary" placeholder="Оклад" />
        </div>
    </spring:bind>

    <spring:bind path="positionId">
        <div class="form-group">
            <div class="col-sm-5">
                <label class="col-sm-2 control-label">Посада</label>
                <form:select path="positionId" class="form-control">
                    <option value="0" label="--- Виберіть ---" />
                    <%--<form:options items="${positionsList}" />--%>
                    <c:forEach items="${positionsList}" var="map">
                        <option value="${map.key}">${map.value}</option>
                    </c:forEach>
                </form:select>
            </div>
            <div class="col-sm-5"></div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn-lg btn-primary pull-right">Додати
                    </button>
        </div>
    </div>
</form:form>

