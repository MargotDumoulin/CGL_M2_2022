package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "Apporteurs",
        urlPatterns = {"/apporteurs"}
)
public class ApporteursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/apporteurs.jsp").forward(request, response);
    }
}
