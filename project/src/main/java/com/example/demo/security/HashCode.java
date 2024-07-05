package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data

public class HashCode {

	 public static String hashcode(String password) throws Exception {
	        byte[] salt = getSalt();
	        String hash = hashPassword(password, salt);
	        String base64Salt = Base64.getEncoder().encodeToString(salt);
	        return hash + " " + base64Salt;
	    }

	    public static byte[] getSalt() throws NoSuchAlgorithmException {
	        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	        byte[] salt = new byte[16];
	        secureRandom.nextBytes(salt);
	        return salt;
	    }

	    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(salt);
	        byte[] hashPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
	        return Base64.getEncoder().encodeToString(hashPassword);
	    }
}
