<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>
    <div class="panel-heading">Страви, що входять в замовлення №${orderId.id} від ${orderId.date}
        (столик ${orderId.tableNum})
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>Назва страви</th>
            <th>Готовність</th>
            <th>Операції</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${dishes}" var="dish">
            <tr>
                <td>${dish.value.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${dish.key.prepared}">
                            Готова
                        </c:when>
                        <c:otherwise>
                            Не готова
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <spring:url value="/orderId/${orderId.id}/dish/${dish.key.id}/update" var="updUrl"/>
                    <spring:url value="/orderId/${orderId.id}/dish/${dish.key.id}/delete" var="deleteUrl"/>
                    <spring:url value="/orderId/${orderId.id}/dish/${dish.key.id}/prepared" var="preparedUrl"/>

                    <button class="btn btn-info" onclick="location.href='${updUrl}'">Деталі</button>
                    <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Видалити</button>

                    <button class="btn btn-warning"
                            <c:choose>
                                <c:when test="${dish.key.prepared}">
                                    disabled
                                </c:when>
                                <c:otherwise>
                                    onclick="this.disabled=true;post('${preparedUrl}')"
                                </c:otherwise>
                            </c:choose>
                    >
                        Відмітити приготування
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <spring:url value="/orderId/${orderId.id}/dish/add" var="userActionUrl"/>

    <form:form modelAttribute="newDish" class="form-horizontal" action="${userActionUrl}"
               method="post">
        <form:hidden path="id"/>
        <spring:bind path="dish">
            <div class="form-group">
                <label class="col-sm-3 control-label">Нова страва:</label>
                <div class="col-sm-9">
                    <form:select path="dish" class="form-control">
                        <c:forEach items="${allDishes}" var="d">
                            <option value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </form:select>
                </div>
            </div>
        </spring:bind>
        <form:hidden path="employee"/>
        <form:hidden path="prepared"/>
        <form:hidden path="orderId"/>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-2">
                <button class="btn btn-info">Додати</button>
            </div>
        </div>
    </form:form>
</div>
