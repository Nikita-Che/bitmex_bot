<%--
  Created by IntelliJ IDEA.
  User: nikita
  Date: 15.12.2023
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Управление потоками</title>
</head>
<body>
<h1>Управление потоками</h1>
<form action="ControllerServlet" method="post">
    <input type="submit" name="startButton" value="Старт">
    <input type="submit" name="stopButton" value="Стоп">
</form>
</body>
</html>
