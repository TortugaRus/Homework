package ru.sberbank.homework.kvasov;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            PluginManager manager = new PluginManager("D:\\Учеба\\Sbertech\\Homework5\\src\\ru\\sberbank\\homework\\kvasov");

            int offset = 2;


            System.out.println("Simple classloader");//
            Plugin plugin = manager.loadPlugin("plugins");
            plugin.run(new String[]{"hello world!"});


            encryptClass(offset);
            System.out.println("Cipher classloader");
            Plugin crypt = manager.loadPlugin("plugins", offset);
            crypt.run(new String[]{"hello cypher world!"});


        } catch (PluginNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void encryptClass(int offset) {
        try (FileInputStream input = new FileInputStream("D:\\Учеба\\Sbertech\\Homework5\\src\\ru\\sberbank\\homework\\kvasov\\plugins\\TestHelloPlugin.class");
             FileOutputStream output = new FileOutputStream("D:\\Учеба\\Sbertech\\Homework5\\src\\ru\\sberbank\\homework\\kvasov\\cryptPlugins\\TestHelloPlugin.class")) {

            byte[] bytes = new byte[input.available()];
            input.read(bytes, 0, bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] + offset);
            }

            input.close();
            output.write(bytes, 0, bytes.length);
            output.flush();
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
