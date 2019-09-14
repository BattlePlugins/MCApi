package mc.alk.mc.plugin;

import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MCPluginManager {

    private MCPlugin plugin;

    public void initializePlugin(PlatformPlugin platformPlugin) {
        InputStream propertyStream = getClass().getResourceAsStream("/plugin.properties");
        BufferedReader reader = new BufferedReader(new InputStreamReader(propertyStream));
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.startsWith("main=")) {
                    String className = line.replace("main=", "");
                    Class<?> pluginClass = Class.forName(className);
                    if (MCPlugin.class.isAssignableFrom(pluginClass))
                        this.plugin = (MCPlugin) pluginClass.newInstance();

                    if (!pluginClass.isAnnotationPresent(PluginProperties.class))
                        throw new RuntimeException("@PluginProperties annotation is not present!");

                    PluginProperties annotation = pluginClass.getAnnotation(PluginProperties.class);

                    //PluginConstants.ID = annotation.id();
                    //PluginConstants.NAME = annotation.name();
                    //PluginConstants.VERSION = annotation.version();
                    //PluginConstants.DESCRIPTION = annotation.description();
                    //PluginConstants.URL = annotation.url();
                    //PluginConstants.AUTHORS = annotation.authors();

                    plugin.setPlatformPlugin(platformPlugin);
                }
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public void enablePlugin() {
        if (plugin != null)
            plugin.onEnable();
        else
            throw new RuntimeException("Plugin is not yet initialized!");
    }

    public void disablePlugin() {
        if (plugin != null)
            plugin.onDisable();
        else
            throw new RuntimeException("Plugin is not initialized!");
    }
}
