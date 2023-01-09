package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Bool;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@WebServlet(
        name = "Apporteurs",
        urlPatterns = {"/apporteurs"}
)
public class ApporteursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Apporteur> apporteurs = new ArrayList<>();
        ApporteurDAO apporteurDAO = new ApporteurDAO();
        CommissionDAO commissionDAO = new CommissionDAO();
        Stream<ApporteurEntity> apporteursEntities = apporteurDAO.getAll();

        apporteursEntities.forEach(s -> {
            LocalDate currentDate = LocalDate.now();
            LocalDate m1Date = LocalDate.from(currentDate).minusMonths(1);
            LocalDate m2Date = LocalDate.from(currentDate).minusMonths(2);

            Optional<Double> resMM1 = commissionDAO.getTotalByMonthAndApporteurId(m1Date.getMonthValue(), m2Date.getYear(), s.getId()).map(Optional::ofNullable).findFirst().flatMap(Function.identity());
            Optional<Double> resMM2 = commissionDAO.getTotalByMonthAndApporteurId(m2Date.getMonthValue(), m2Date.getYear(),  s.getId()).map(Optional::ofNullable).findFirst().flatMap(Function.identity());
            Optional<Double> resMC = commissionDAO.getTotalByMonthAndApporteurId(currentDate.getMonthValue(), currentDate.getYear(), s.getId()).map(Optional::ofNullable).findFirst().flatMap(Function.identity());
            Boolean isAffilie = apporteurDAO.getIsAffilie(s.getId());

            apporteurs.add(new Apporteur(s.getId(), isAffilie, s.getNom(), s.getPrenom(), resMC.orElse(0.0), resMM1.orElse(0.0), resMM2.orElse(0.0)));
        });

        request.setAttribute("apporteurs", apporteurs);
        request.getRequestDispatcher("/apporteurs.jsp").forward(request, response);
    }
}
