package com.ufide.vetzone;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "1234";

        String encryptedPassword = encoder.encode(password);

        System.out.println("Contraseña original: " + password);
        System.out.println("Contraseña BCrypt: " + encryptedPassword);
    }
}

