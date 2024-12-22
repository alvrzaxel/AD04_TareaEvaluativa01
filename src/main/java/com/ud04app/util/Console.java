/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase utilitaria para gestionar la entrada del usuario desde la consola
 * Proporciona métodos para leer cadenas, números enteros y números de punto flotante,
 * manejando errores de entrada
 */
public class Console {
    
    // Objeto BufferedReader para leer entradas desde la consola
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    // Lee una cadena y maneja excepciones hasta que se introduzca un valor válido
    
    /**
     * Lee una cadena desde la consola
     * Solicita al usuario una cadena de texto, lo valida y lo retorna
     * @param msg Mensaje que se muestra al usuario para solicitar la entrada
     * @return La cadena introducida por el usuario sin espacios adicionales
     */
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
    
    /**
     * Lee un número entero desde la consola
     * Solicita al usuario un número, valida su formato y lo retorna
     * @param msg Mensaje que se muestra al usuario para solicitar la entrada
     * @return El número entero introducido por el usuario
     */
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
    /**
     * Lee un número de tipo double desde la consola
     * Solicita al usuario un número decimal, valida su formato y lo retorna
     * @param msg Mensaje que se muestra al usuario para solicitar la entrada
     * @return El número decimal introducido por el usuario
     */
    public static double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(in.readLine().trim());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Entrada incorrecta. Introduce un número doble válido.");
            }
        }
    }
}