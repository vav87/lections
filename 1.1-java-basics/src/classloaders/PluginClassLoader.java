package classloaders;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {

        URLClassLoader pluginClassLoader =
                new URLClassLoader(new URL[]{new URL("file://home/user123/MyPlugin.jar")});

        Class<?> clazz = pluginClassLoader.loadClass("MyClassName");
    }
}
