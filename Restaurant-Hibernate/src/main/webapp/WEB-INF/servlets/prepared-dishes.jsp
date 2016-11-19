<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:url value="/order/prepareddish/update" var="userActionUrl"/>
<div class="container">
    <h1>Оновити деталі страви (замовлення №${preparedDish.orderId})</h1>
    <br/>

    <form:form class="form-horizontal" method="post"
               modelAttribute="preparedDish" action="${userActionUrl}">

        <form:hidden path="id"/>
        <form:hidden path="dishId"/>
        <form:hidden path="orderId"/>
        <div class="form-group">
            <label class="col-sm-3 control-label">Страва:</label>
            <label class="col-sm-9 control-label">${dish.name}</label>
        </div>
        <spring:bind path="employeeId">
            <div class="form-group">
                <label for="employeeId" class="col-sm-3 control-label">Хто готує:</label>
                <div class="col-sm-9">
                    <form:select path="employeeId" class="form-control">
                        <option value="0">--- Виберіть ---</option>
                        <c:forEach items="${employees}" var="employee">
                            <option value="${employee.id}"
                                    <c:if test="${preparedDish['employeeId']==employee.id}">selected</c:if> >${employee.firstName} ${employee.lastName}</option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
        </spring:bind>
        <form:hidden path="prepared"/>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-2">
                <button type="submit" class="btn-lg btn-primary pull-right">Оновити</button>
            </div>
        </div>
    </form:form>
</div>