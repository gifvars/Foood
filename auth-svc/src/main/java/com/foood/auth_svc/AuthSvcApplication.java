package com.foood.auth_svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.foood.auth_svc",
        "com.foood.commons_svc"   // package of the util service
})
public class AuthSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSvcApplication.class, args);
	}

}
