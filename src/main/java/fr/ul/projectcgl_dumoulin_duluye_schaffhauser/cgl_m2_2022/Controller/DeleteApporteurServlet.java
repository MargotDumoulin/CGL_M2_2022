package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(
        name = "Supprimer un apporteur",
        urlPatterns = {"/delete_apporteur"}
)
public class DeleteApporteurServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ApporteurDAO.getInstance().delete(
                Optional.ofNullable(request.getParameter("appId"))
                        .map(Long::parseLong)
                        .orElse(null));

        response.sendRedirect("apporteurs");
    }
}
