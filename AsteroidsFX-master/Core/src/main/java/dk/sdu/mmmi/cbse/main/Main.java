package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.GameData;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.File;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {

        // Load plugins from the plugins folder using a separate ModuleLayer
        Path pluginsPath = new File("plugins/mods-mvn").toPath();
        System.out.println("Looking for plugins at: " + pluginsPath.toAbsolutePath());
        ModuleFinder pluginsFinder = ModuleFinder.of(pluginsPath);
        pluginsFinder.findAll().forEach(m -> System.out.println("Found module: " + m.descriptor().name()));
        ModuleLayer parentLayer = ModuleLayer.boot();
        Configuration config = parentLayer.configuration()
                .resolve(pluginsFinder, ModuleFinder.of(), List.of("Bullet", "Player", "Enemy", "Asteroid"));
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        ModuleLayer pluginsLayer = parentLayer.defineModulesWithOneLoader(config, loader);

        GameData.setPluginsLayer(pluginsLayer);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean(ModuleLayer.class, () -> pluginsLayer);
        ctx.register(ModuleConfig.class);
        ctx.refresh();

        Game game = ctx.getBean(Game.class);
        game.start(window);
        game.render();
    }
}