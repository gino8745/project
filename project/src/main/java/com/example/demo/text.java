package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import org.apache.tomcat.jni.Sockaddr;

import com.example.demo.security.HashCode;

public class text {
	public static void main(String[] args) {
		
	}
	
	public class Animal {
	    public void makeSound() {
	        System.out.println("Animal makes a sound");
	    }
	}

	// 子类
	public class Dog extends Animal {
	    @Override
	    public void makeSound() {
	        System.out.println("Dog barks");
	    }
	}
}
