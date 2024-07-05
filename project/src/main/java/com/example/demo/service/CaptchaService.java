package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CaptchaService {
   
	private  Producer producer;
	
	   public CaptchaService() {
	         Properties properties=new Properties();
	         properties.setProperty("kaptcha.image.width", "150");
	         properties.setProperty("kaptcha.image.height", "50");
	         properties.setProperty("kaptcha.textproducer.font.color", "black");
	         properties.setProperty("kaptcha.textproducer.char.space", "5");   
	         Config config=new Config(properties);
	         producer=config.getProducerImpl();
	   }
	   
	   public void generateCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
		    String captchaText =producer.createText();
		    BufferedImage captchaImage=producer.createImage(captchaText);
		    
		    request.getSession().setAttribute("captcha", captchaText);
		    
		    response.setContentType("image/jpeg");
		    ImageIO.write(captchaImage, "jpg", response.getOutputStream());
	   };
	
	   public boolean validateCaptcha(String inputCaptcha,HttpServletRequest request) {
		
		   String captcha =(String) request.getSession().getAttribute("captcha");
				   
		   return captcha != null && captcha.equalsIgnoreCase(inputCaptcha);
	   }
	
	
}
