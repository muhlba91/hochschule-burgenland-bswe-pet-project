package io.muehlbachler.bswe.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the configuration for the APIs.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface ApiConfiguration {
  /**
   * Returns the connection information for the geocoding API.
   *
   * @return the connection information for the geocoding API
   */
  ApiConnectionInformation getGeocoding();

  /**
   * Represents the connection information for an API.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  class ApiConnectionInformation {
    private String url;
  }

}
