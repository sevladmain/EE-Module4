<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:url value="/employee/added" var="userActionUrl"/>

<form:form class="form-vertical" method="post"
           modelAttribute="employeeForm" action="${userActionUrl}">

    <form:hidden path="id"/>

    <spring:bind path="firstName">
        <div class="input-group">
            <label for="name" class="col-sm-6 control-label">Ім'я</label>
            <div class="col-sm-12">
                <form:input path="firstName" type="text" class="form-control" aria-describedby="basic-addon1"
                            id="name" placeholder="Ім'я"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="lastName">
        <div class="input-group">
            <label for="lastName" class="col-sm-6 control-label">Прізвище</label>
            <div class="col-sm-12">
                <form:input path="lastName" type="text" class="form-control" aria-describedby="basic-addon1"
                            id="lastName" placeholder="Прізвище"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="dateBirth">
        <div class="input-group">
            <label for="dateBirth" class="col-sm-6 control-label">Дата народження</label>
        <div class="col-sm-12">
            <form:input path="dateBirth" type="text" class="form-control" aria-describedby="basic-addon1"
                        id="dateBirth" placeholder="Дата народження"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="salary">
        <div class="input-group">
            <label for="salary" class="col-sm-6 control-label">Оклад</label>
        <div class="col-sm-12">
            <form:input path="salary" type="text" class="form-control" aria-describedby="basic-addon1"
                        id="salary" placeholder="Оклад"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="positionId">
        <div class="form-group">
            <div class="col-sm-6">
                <label for="salary" class="col-sm-6 control-label">Посада</label>
                <form:select path="positionId" class="form-control">
                    <option value="0" label="--- Виберіть ---"/>
                    <%--<form:options items="${positionsList}" />--%>
                    <c:forEach items="${positionsList}" var="map">
                        <option value="${map.key}">${map.value}</option>
                    </c:forEach>
                </form:select>
            </div>
            <div class="col-sm-6"></div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-2">
            <button type="submit" class="btn-lg btn-primary pull-center">Додати
            </button>
        </div>
    </div>
</form:form>

