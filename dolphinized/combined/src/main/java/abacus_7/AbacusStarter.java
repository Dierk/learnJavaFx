package abacus_7;

import javafx.application.Application;
import org.opendolphin.LogConfig;
import org.opendolphin.core.client.comm.InMemoryClientConnector;
import org.opendolphin.core.client.comm.JavaFXUiThreadHandler;
import org.opendolphin.core.comm.DefaultInMemoryConfig;

public class AbacusStarter {

    public static void main(String[] args) throws Exception {

        DefaultInMemoryConfig config = makeJavaFXInMemoryConfig();

        AbacusDirector director = new AbacusDirector();
        director.setServerDolphin(config.getServerDolphin());
        config.getServerDolphin().register(director);

        AbacusApplication.dolphin = config.getClientDolphin();
        Application.launch(AbacusApplication.class);
    }

    // the part of the starter that is application independent
    private static DefaultInMemoryConfig makeJavaFXInMemoryConfig() {
        DefaultInMemoryConfig config = new DefaultInMemoryConfig();
        config.getConnector().setUiThreadHandler(new JavaFXUiThreadHandler());
        ((InMemoryClientConnector) config.getConnector()).setSleepMillis(0);
        config.getServerDolphin().registerDefaultActions();
        LogConfig.noLogs();
        return config;
    }

}
