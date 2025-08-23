package com.br.consultas.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

	public static String gerarId(String input) {
		return hashMD5(input);
	}

	private static String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Preenche com zeros à esquerda para ter sempre 32 caracteres
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static String encryptPassword(String plainPassword) {
        // Gera o salt automaticamente
        String salt = BCrypt.gensalt(12); // 12 = fator de custo (quanto maior, mais seguro, mas mais lento)
        return BCrypt.hashpw(plainPassword, salt);
    }
	
	public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
