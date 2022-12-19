package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "Affaires",
        urlPatterns = {"/affaires"}
)
public class AffairesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Basic example of ArrayList injection
        List<String> affaires = new ArrayList<String>();
        affaires.add("Johnny");
        affaires.add("Tim");

        request.setAttribute("affaires", affaires);
        request.getRequestDispatcher("/affaires.jsp").forward(request, response);
    }
}
