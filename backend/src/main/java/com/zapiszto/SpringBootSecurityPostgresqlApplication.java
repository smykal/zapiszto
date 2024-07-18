package com.zapiszto;

import io.sentry.spring.jakarta.EnableSentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@EnableSentry(dsn = "https://76450b32eb9e398f6154876b6549e1f0@o4507619066511360.ingest.de.sentry.io/4507619069460560")
@Configuration
@SpringBootApplication
public class SpringBootSecurityPostgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityPostgresqlApplication.class, args);
	}

}
