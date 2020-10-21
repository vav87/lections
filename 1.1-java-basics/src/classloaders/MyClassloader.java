package classloaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassloader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("fileName"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
        return clazz;
    }
}
