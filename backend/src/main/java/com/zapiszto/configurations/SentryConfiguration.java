package com.zapiszto.configurations;

import io.sentry.Sentry;
import io.sentry.SentryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"docker", "local", "render"})
@Configuration
public class SentryConfiguration {

  @Value("${sentry.environment}")
  private String environment;

  @Value("${sentry.dsn}")
  private String dsn;

  @Bean
  public Sentry.OptionsConfiguration<SentryOptions> configureSentry() {
    return options -> {
      options.setDsn(dsn);
      options.setEnvironment(environment);
    };
  }
}
