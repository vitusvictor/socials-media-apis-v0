package com.socialsapis.socialmediaapis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(
//		title = "SOCIAL MEDIA APIs",
//		description = "SOCIAL MEDIA APIs",
//		version = "v1",
//		contact = @Contact(
//				name = "Somto",
//				email = "vitusvictor41@gmail.com"
//		)
//)
//)
@EnableSwagger2
public class SocialMediaApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaApisApplication.class, args);
	}

}
