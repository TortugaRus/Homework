package ru.sberbank.homework.kvasov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PluginClassLoader extends ClassLoader {

    private String pathPlugins;
    private Map<String, Class<?>> storeClasses = new HashMap<>();

    public PluginClassLoader(String pathPlugins) {
        super(Main.class.getClassLoader());
        this.pathPlugins = pathPlugins;
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            return findClass(className);
        } catch (ClassNotFoundException ex) {
            return super.loadClass(className);
        }
    }

    protected Class<?> findClass(String binaryClassName) throws ClassNotFoundException {
        Class<?> resultClass;
        try {
            if (storeClasses.containsKey(binaryClassName)) {
                resultClass = storeClasses.get(binaryClassName);
            } else {
                Path path = Paths.get(pathPlugins + "\\" + binaryClassName + ".class");

                byte[] byteClass = Files.readAllBytes(path);
                resultClass = defineClass(null, byteClass, 0, byteClass.length);
                storeClasses.put(binaryClassName, resultClass);
            }
        } catch (IOException ex) {
            throw new ClassNotFoundException();
        }
        return resultClass;
    }
}
