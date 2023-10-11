<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/result.css">
    <link rel="icon" type="image/png" href="favicon.png">
    <title>Result</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <td>X</td>
                <td>Y</td>
                <td>R</td>
                <td>Попал</td>
                <td>Создано</td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${resultX}</td>
                <td>${resultY}</td>
                <td>${resultR}</td>
                <td>${resultHit}</td>
                <td>${resultCreatedAt}</td>
            </tr>
        </tbody>
    </table>
    <div class="spacer"></div>
    <div class="back-btn">
        <a href="${pageContext.request.contextPath}/">Назад</a>
    </div>
</body>
</html>
