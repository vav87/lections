package classloaders;

import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String[] args) {
        URLClassLoader pluginClassLoader = new URLClassLoader(new URL[]{new URL("file://home/user123/MyPlugin.jar")});
        Class clazz = pluginClassLoader.loadClass("");
    }
}
