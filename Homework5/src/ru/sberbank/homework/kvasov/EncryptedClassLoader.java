package ru.sberbank.homework.kvasov;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader {

    private String pathPlugins;
    private int ofsset;

    public EncryptedClassLoader(String pathPlugins, int offset) {
        super(Main.class.getClassLoader());
        this.pathPlugins = pathPlugins;
        this.ofsset = offset;
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
        String path = pathPlugins + "\\" + binaryClassName + ".class";
        byte[] byteClass = decryptClass(path, ofsset);
        resultClass = defineClass(null, byteClass, 0, byteClass.length);
        return resultClass;
    }

    private byte[] decryptClass(String path, int offset) {
        try {
            FileInputStream input = new FileInputStream(path);
            byte[] result = new byte[input.available()];

            int x = -1;
            int j = 0;
            while ((x = input.read()) != -1) {
                result[j] = (byte) x;
                j++;
            }
            input.close();

            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) (result[i] - offset);
            }
            return result;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }
}
