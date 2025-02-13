package WebServer;

import RequestHandler.RequestHandler;
import Router.Router;
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

        // Criando um diretório temporário para o Tomcat
        File baseDir = new File("tomcat-temp");
        baseDir.mkdir();
        tomcat.setBaseDir(baseDir.getAbsolutePath());

        // Criando um contexto básico
        Context ctx = tomcat.addContext("/", baseDir.getAbsolutePath());

        Tomcat.addServlet(ctx,"RequestHandler", new RequestHandler());
        ctx.addServletMappingDecoded("/*","RequestHandler");
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
        WebServer webServer = new WebServer(8080);
        Router.registerControllers(); // Escaneia e registra os controladores
        webServer.start();

    }




}
