package org.example.bibliomanager.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {


    public static String hashPassword(String password) {
        try {
            // Obtener el objeto MessageDigest para SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Aplicar el algoritmo de hash a la contraseña
            byte[] hashBytes = md.digest(password.getBytes());

            // Convertir los bytes del hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                // Convertir byte a su representación hexadecimal
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción en caso de que el algoritmo no esté disponible
            e.printStackTrace();
            return null;
        }
    }
}