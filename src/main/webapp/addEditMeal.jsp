<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
    <div align="center">
        <form method="POST" action='meals' name="frmAddMeal">
            <c:set var="formatter" value="${formatter}" />

            DateTime: <input type="datetime-local" name="date" value="${meal.dateTime.format(formatter)}"><br />

            Описание: <input type="text" name="description" value="${meal.description}"/><br/>

            Калории: <input type="text" name="calories" value="${meal.calories}"/><br/>

            <input type="hidden" name="mealId" value="${meal.id}">

            <input type="submit" value="Сохранить"/>
        </form>
    </div>
</body>
</html>