<%@ page import="web.lab2.server.models.CoordsData" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  ServletContext servletContext = request.getServletContext();
  List<CoordsData> results = (List<CoordsData>) servletContext.getAttribute("resultsCollection");
  if (results == null) {
    results = new ArrayList<CoordsData>();
  }
  Collections.reverse(results);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/index.css">
  <link rel="icon" type="image/png" href="favicon.png">
  <script src="index.js"></script>
  <title>Lab 2</title>
</head>
<body>
<table>
  <thead>
  <tr>
    <td>
      <h1>Lab 2</h1>
    </td>
    <td id="lab-info">
      Сандов Кирилл Алексеевич / P3213 / Вариант 2310
    </td>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td class="coords-info">
      <div class="coords-interactive-container">
          <img src="areas.png" alt="coords" id="coords-interactive"/>
          <%
            for (CoordsData coords : results) {
              String pointHTML = String.format(
                Locale.US,
                "<div class=\"results-point\" x=\"%.3f\" y=\"%.3f\" r=\"%.3f\"></div>",
                coords.x(),
                coords.y(),
                coords.r()
              );
              out.println(pointHTML);
            }
          %>
      </div>
    </td>
    <td>
      <form id="coords-form" method="post" action="${pageContext.request.contextPath}/controller">
        <table>
          <tr>
            <td class="form-label">X:</td>
            <td>
              <input type="text" name="x" id="x-coord-input" list="x-select"/>
              <datalist id="x-select">
                <option>-3</option>
                <option>-2</option>
                <option>-1</option>
                <option>0</option>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
              </datalist>
            </td>
          </tr>
          <tr>
            <td class="form-label">Y:</td>
            <td><input name="y" id="y-coord-input"/></td>
          </tr>
          <tr>
            <td class="form-label">R:</td>
            <td>
              <select name="r" id="r-coord-input">
                <option>...</option>
                <option>1</option>
                <option>1.5</option>
                <option>2</option>
                <option>2.5</option>
                <option>3</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <input type="hidden" name="timezone" id="timezone-input" value="UTC"/>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <input class="coords-btn" id="coords-submit" type="submit" value="Отправить"/>
            </td>
          </tr>
          <tr>
            <td></td>
          </tr>
        </table>
      </form>
    </td>
  </tr>
  <tr class="results-row">
    <td colspan="2">
        <table class="results-table">
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
              <%
                for (CoordsData coords : results) {
                  out.println("<tr>");
                  out.println("<td>" + coords.x() + "</td>");
                  out.println("<td>" + coords.y() + "</td>");
                  out.println("<td>" + coords.r() + "</td>");
                  out.println("<td>" + coords.checkHit() + "</td>");
                  out.println("<td>" + coords.formattedCreatedAt() + "</td>");
                  out.println("</tr>");
                }
              %>
            </tbody>
        </table>
    </td>
  </tr>
  </tbody>
</table>
</body>
</html>