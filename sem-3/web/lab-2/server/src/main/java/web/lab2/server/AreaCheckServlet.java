package web.lab2.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.lab2.server.models.CoordsData;


@WebServlet(name = "areaCheckServlet", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rawX = req.getParameter("x");
        String rawY = req.getParameter("y");
        String rawR = req.getParameter("r");

        CoordsData data = CoordsData.validateAndCreate(rawX, rawY, rawR);

        processCoordsData(data, req, resp);
    }

    private void processCoordsData(CoordsData coordsData, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        
        servletContext.setAttribute("resultX", coordsData.x());
        servletContext.setAttribute("resultY", coordsData.y());
        servletContext.setAttribute("resultR", coordsData.r());
        servletContext.setAttribute("resultHit", coordsData.checkHit());
        servletContext.setAttribute("resultCreatedAt", coordsData.formattedCreatedAt());
        addToResultsCollection(coordsData);
        
        servletContext.getRequestDispatcher("/result").forward(req, resp);
    }
    
    private void addToResultsCollection(CoordsData coordsData) {
        ServletContext servletContext = getServletContext();
        
        List<CoordsData> resultsCollection = (List<CoordsData>) servletContext.getAttribute("resultsCollection");
        if (resultsCollection == null) {
            resultsCollection = new ArrayList<CoordsData>();
        }
        resultsCollection.add(coordsData);
        servletContext.setAttribute("resultsCollection", resultsCollection);
    }
}
