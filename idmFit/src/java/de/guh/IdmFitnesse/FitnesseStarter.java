package de.guh.IdmFitnesse;

import java.io.IOException;
import java.util.logging.Logger;

import fitnesse.ContextConfigurator;
import fitnesse.Shutdown;
import fitnesse.http.ResponseParser;
import fitnesseMain.FitNesseMain;
 
public class FitnesseStarter {
	private static final Logger LOG = Logger.getLogger(Shutdown.class.getName());
    private static ContextConfigurator contextConfigurator;

	public static void main(String args[]) throws Exception {
        FitnesseStarter.start();
    }
 
    public static void start() throws Exception {
    	FitNesseMain fMain = new FitNesseMain();
    	contextConfigurator = ContextConfigurator.systemDefaults();
    	contextConfigurator.withPort(8080);
    	contextConfigurator.withRootPath("./dbfit");
    	fMain.launchFitNesse(contextConfigurator);
    	
//        FitNesseContext context = loadContext();
//        FitNesse fitnesse = new FitNesse(context);
//        fitnesse.applyUpdates();
//        boolean started = fitnesse.start();
//        if (started)
//            System.out.println(context);
    }
    
    private static void stop() {
    	if (contextConfigurator == null) {
			contextConfigurator = ContextConfigurator.systemDefaults();
		}
    	Shutdown shutdown = new Shutdown();
    	ResponseParser response;
		try {
			response = shutdown.buildAndSendRequest();
			String status = shutdown.checkResponse(response);
			if (!"OK".equals(status)) {
				LOG.warning("Failed to shutdown. Status = " + status);
				System.exit(response.getStatus());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.warning("Failed to shutdown fitnesse: " + e.getMessage());
			e.printStackTrace();
		}

	}
     
//    protected FitNesseContext loadContext() throws Exception {
//        FitNesseContext context = new FitNesseContext();
//        context.port = 8090;
//        context.rootPath = "./src/test/fitnesse";
//        context.rootPagePath = context.rootPath + "/" + context.rootDirectoryName;
//        ComponentFactory componentFactory = new ComponentFactory(context.rootPath);
//        WikiPageFactory wikiPageFactory = new WikiPageFactory();
//        context.root = wikiPageFactory.makeRootPage(context.rootPath, context.rootDirectoryName, componentFactory);
// 
//        context.responderFactory = new ResponderFactory(context.rootPagePath);
//        VelocityFactory.makeVelocityFactory(context);
//        VelocityEngine engine = new VelocityEngine();
//        engine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "FitNesseRoot/files/templates");
//        VelocityFactory.setVelocityEngine(engine);
//        System.setProperty("eclipsepath", System.getProperty("java.class.path"));
//        return context;
//    }
}
