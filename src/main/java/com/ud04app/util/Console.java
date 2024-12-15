package com.ud04app.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    // Lee una cadena y maneja excepciones hasta que se introduzca un valor válido
    public static String readString(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return in.readLine().trim();
            } catch (IOException e) {
                System.out.println("Entrada incorrecta. Introduce un texto valido.");
            }
        }
    }
    
    // Lee un int y maneja excepciones hasta que se introduzca un valor válido
    public static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(in.readLine().trim());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Entrada incorrecta. Introduce un número entero valido.");
            }
        }
    }
    
    // Lee un double y maneja excepciones hasta que se introduzca un valor válido
    public static double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(in.readLine().trim());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Entrada incorrecta. Introdue un número doble válido.");
            }
        }
    }
}