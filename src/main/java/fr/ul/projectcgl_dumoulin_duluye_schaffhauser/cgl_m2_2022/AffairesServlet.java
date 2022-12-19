package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(
        name = "Affaire",
        urlPatterns = {"/affaire"}
)
public class AffaireServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");
        request.setAttribute("text", message);
        request.getRequestDispatcher("/affaire.jsp").forward(request, response);
    }
}
