package io.muehlbachler.bswe.configuration.impl;

import io.muehlbachler.bswe.configuration.ApiConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for external APIs.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfigurationProperties implements ApiConfiguration {
  private ApiConnectionInformation geocoding;
}
