package main.java.ru.digiralhabbits.lecture.gc;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryMetaspace {

    // -XX:MaxMetaspaceSize=12M
    public static void main(String[] args)
            throws Exception {
        int count = 1_000_000;
        final List<Object> instances = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Загружаем класс
            final URL resource = new File("plugins/test-plugin.jar").toURI().toURL();
            final URLClassLoader childClassLoader =
                    new URLClassLoader(new URL[]{ resource }, OutOfMemoryMetaspace.class.getClassLoader());
            final Class<?> cls = Class.forName("ru.digiralhabbits.lecture.gc.TestPlugin", true, childClassLoader);
            final Object instance = cls.getDeclaredConstructor().newInstance();
            final Method method = cls.getDeclaredMethod("execute");

            System.out.printf("%d\t%s [%s]\n", i, instance, ((String)method.invoke(instance)).substring(0, 10));
            // Сохраняем его, чтобы GC не удалил ссылки и данные о нем остались в Metaspace
            instances.add(instance);
        }

        System.out.printf("%d\b", instances.size());
    }
}


