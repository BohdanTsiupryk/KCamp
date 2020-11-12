package bts.KCamps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class KCampsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KCampsApplication.class, args);
	}

}
