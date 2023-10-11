package web.lab2.server;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.lab2.server.models.CoordsData;


@WebServlet(name = "controllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rawX = req.getParameter("x");
        String rawY = req.getParameter("y");
        String rawR = req.getParameter("r");

        try {
            CoordsData data = CoordsData.validateAndCreate(rawX, rawY, rawR);
            processCoordsForm(data, req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(418);
        }
    }

    private void processCoordsForm(CoordsData coordsData, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/area-check").forward(req, resp);
    }
}
