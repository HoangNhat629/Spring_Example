package vn.tayjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TayJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TayJavaApplication.class, args);
	}

}
