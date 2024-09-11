package io.muehlbachler.bswe.service;

import io.muehlbachler.bswe.model.Coordinates;

/**
 * A service to handle all geocoding related actions.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface GeocodingService {
  /**
   * Fetches the coordinates for the given location.
   *
   * @param location the location for which the coordinates should be fetched
   * @return the coordinates for the given location
   */
  Coordinates fetchCoordinates(String location);
}
