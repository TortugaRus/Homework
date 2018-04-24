package ru.sberbank.homework.kvasov;

import sun.plugin.dom.exception.PluginNotSupportedException;

import java.io.File;
import java.util.LinkedList;

public class PluginManager {
    private final String rootDirectory;

    public PluginManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public Plugin loadPlugin(String pluginName) throws PluginNotFoundException {

        String pathToClasses = rootDirectory + "\\" + pluginName;
        ClassLoader pluginClassLoader = new PluginClassLoader(pathToClasses);
        String possibleClass = getNameClass(pathToClasses, pluginName).replaceAll(".class", "");

        try {
            return (Plugin) pluginClassLoader.loadClass(possibleClass).newInstance(); //!

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
        }
    }

    public Plugin loadPlugin(String pluginName, int offset) throws PluginNotFoundException {
        String pathToClasses = rootDirectory + "\\" + pluginName;
        ClassLoader pluginClassLoader = new EncryptedClassLoader(pathToClasses, offset);
        String possibleClass = getNameClass(pathToClasses, pluginName).replaceAll(".class", "");
        try {
            return (Plugin) pluginClassLoader.loadClass(possibleClass).newInstance(); //!

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
        }
    }

    private String getNameClass(String path, String pluginName) throws PluginNotFoundException {
        File pluginsFolder = new File(path);
        File[] classes = pluginsFolder.listFiles();
        if (classes.length == 0 || classes == null) {
            throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
        }

        LinkedList<File> plugins = new LinkedList<>();

        for (File possiblePlugin : classes) {

            if (possiblePlugin.getName().endsWith(".class")) {
                plugins.add(possiblePlugin);
            }
        }
        return plugins.get(0).getName();
    }
}
