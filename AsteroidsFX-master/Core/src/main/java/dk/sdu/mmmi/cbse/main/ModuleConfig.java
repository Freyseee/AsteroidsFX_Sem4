package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ModuleConfig {

    private final ModuleLayer pluginsLayer;

    public ModuleConfig(ModuleLayer pluginsLayer) {
        this.pluginsLayer = pluginsLayer;
    }

    @Bean
    public Game game(){
        return new Game(gamePluginServices(), entityProcessingServiceList(), postEntityProcessingServices(), serviceClient());
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList(){
        List<IEntityProcessingService> services = new ArrayList<>();
        ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).forEach(services::add);
        ServiceLoader.load(pluginsLayer, IEntityProcessingService.class).stream()
                .map(ServiceLoader.Provider::get)
                .filter(s -> services.stream().noneMatch(existing -> existing.getClass().getName().equals(s.getClass().getName())))
                .forEach(services::add);
        return services;
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        List<IGamePluginService> services = new ArrayList<>();
        ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).forEach(services::add);
        ServiceLoader.load(pluginsLayer, IGamePluginService.class).stream()
                .map(ServiceLoader.Provider::get)
                .filter(s -> services.stream().noneMatch(existing -> existing.getClass().getName().equals(s.getClass().getName())))
                .forEach(services::add);
        return services;
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        List<IPostEntityProcessingService> services = new ArrayList<>();
        ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).forEach(services::add);
        ServiceLoader.load(pluginsLayer, IPostEntityProcessingService.class).stream()
                .map(ServiceLoader.Provider::get)
                .filter(s -> services.stream().noneMatch(existing -> existing.getClass().getName().equals(s.getClass().getName())))
                .forEach(services::add);
        return services;
    }

    @Bean
    public ServiceClient serviceClient() {
        return new ServiceClient();
    }
}