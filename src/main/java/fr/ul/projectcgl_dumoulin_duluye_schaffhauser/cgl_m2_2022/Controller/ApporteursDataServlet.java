package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import com.google.gson.Gson;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@WebServlet(

        name = "ApporteursData",
        urlPatterns = {"/apporteurs-data"}
)
public class ApporteursDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get pagination parameters
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int pageSize = Integer.parseInt(request.getParameter("length"));

        // Get and initialize apporteurs
        List<Apporteur> apporteurs = new ArrayList<>();
        Stream<ApporteurEntity> apporteursEntities = ApporteurDAO.getInstance().getAll(pageSize, start);
        Long numberOfResults = ApporteurDAO.getInstance().getAll().count();

        apporteursEntities.forEach(s -> {
            LocalDate currentDate = LocalDate.now();
            LocalDate m1Date = LocalDate.from(currentDate).minusMonths(1);
            LocalDate m2Date = LocalDate.from(currentDate).minusMonths(2);

            Optional<Double> resMM1 = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(m1Date.getMonthValue(), m1Date.getYear(), s.getId()).map(Optional::ofNullable).findFirst().flatMap(Function.identity());
            Optional<Double> resMM2 = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(m2Date.getMonthValue(), m2Date.getYear(), s.getId()).map(Optional::ofNullable).findFirst().flatMap(Function.identity());
            Optional<Double> resMC = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(currentDate.getMonthValue(), currentDate.getYear(), s.getId()).map(Optional::ofNullable).findFirst().flatMap(Function.identity());
            Boolean isAffilie = ApporteurDAO.getInstance().getIsAffilie(s.getId());

            apporteurs.add(new Apporteur(s.getId(), isAffilie, s.getNom(), s.getPrenom(), resMC.orElse(0.0), resMM1.orElse(0.0), resMM2.orElse(0.0)));
        });

        // Send response
        String jsonApporteurs = new Gson().toJson(apporteurs);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"draw\":" + draw + ",\"data\":" + jsonApporteurs + ", \"recordsTotal\":" + numberOfResults + ",\"recordsFiltered\":" + numberOfResults + "}");
    }
}
