import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.apache.tomee.catalina.TomEEWebappClassLoader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/rest")
public class TestRest {
    @GET
    public Response ping() {
        String allClasses = "";

        try (ScanResult scanResult =
                     new ClassGraph()
                             .verbose()               // Log to stderr
                             .enableAllInfo()         // Scan classes, methods, fields, annotations
                             .acceptPackages("de.test.classgraph")
                             //.overrideClassLoaders(new TomEEWebappClassLoader())
                             .scan()) {               // Start the scan
            for(String name : scanResult.getAllClasses().getNames()) {
                allClasses += name + "\n";
            }
        }

        return Response.ok().entity(allClasses).build();
    }
}