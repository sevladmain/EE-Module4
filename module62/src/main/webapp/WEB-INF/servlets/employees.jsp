<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">Перелік працівників</div>
    <table class="table">
        <thead>
        <tr>
            <th>Ім'я</th>
            <th>Прізвище</th>
            <th>Дата народження</th>
            <th>Посада</th>
            <th>Оклад</th>
            <th>Операції</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.key.firstName}</td>
                <td>${employee.key.lastName}</td>
                <td>${employee.key.dateBirth}</td>
                <td>${employee.value.position}</td>
                <td>${employee.key.salary}</td>
                <td><a href= "/delete-employee/${employee.key.id}">Видалити</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
