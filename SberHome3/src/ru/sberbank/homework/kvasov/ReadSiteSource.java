package ru.sberbank.homework.kvasov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ReadSiteSource {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите URL: ");
            String url = sc.nextLine();
            if (url.equals("exit")) {
                break;
            }
            try {
                readPage(url);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void readPage(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Нет доступа к ресурсу");
        }
        try {
            BufferedReader bufferedReaderUrl = new BufferedReader(
                    new InputStreamReader(url.openConnection().getInputStream()));
            String result;

            while ((result = bufferedReaderUrl.readLine()) != null) {
                System.out.println(result);
            }
            bufferedReaderUrl.close();
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка при чтении с ресурса");
        }
    }
}
