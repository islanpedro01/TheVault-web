package webserver;

import requesthandler.RequestHandler;
import router.Router;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class WebServer {
    private final Tomcat tomcat;

    public WebServer(int port){
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        tomcat.getConnector().setURIEncoding("UTF-8"); // Define encoding da URI
        tomcat.getConnector().setProperty("useBodyEncodingForURI", "true");

        // Criando um diretório temporário para o Tomcat
        File baseDir = new File("tomcat-temp");
        baseDir.mkdir();
        tomcat.setBaseDir(baseDir.getAbsolutePath());

        // Criando um contexto básico
        Context ctx = tomcat.addContext("/", baseDir.getAbsolutePath());
        ctx.setRequestCharacterEncoding("UTF-8");
        ctx.setResponseCharacterEncoding("UTF-8");

        Tomcat.addServlet(ctx, "requesthandler", new RequestHandler());
        ctx.addServletMappingDecoded("/*", "requesthandler");
    }
    public void start() throws LifecycleException {
        tomcat.start();
        System.out.println("Servidor Tomcat rodando na porta " + tomcat.getConnector().getPort());
        tomcat.getServer().await();
    }
    public void stop() throws LifecycleException {
        tomcat.stop();
    }

    public static void main(String[] args) throws LifecycleException {
        System.setProperty("file.encoding", "UTF-8");
        WebServer webServer = new WebServer(8080);
        Router.registerControllers("controllers"); // Escaneia e registra os controladores
        webServer.start();

    }




}
