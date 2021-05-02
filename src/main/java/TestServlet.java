import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = "/servlet")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ScanResult scanResult =
                     new ClassGraph()
                             .verbose()               // Log to stderr
                             .enableAllInfo()         // Scan classes, methods, fields, annotations
                             .acceptPackages("de.test.classgraph")
                             .scan()) {               // Start the scan
            for(String name : scanResult.getAllClasses().getNames()) {
                resp.getOutputStream().println(name);
            }
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}