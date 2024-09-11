package io.muehlbachler.bswe.service.impl;

import io.muehlbachler.bswe.configuration.ApiConfiguration;
import io.muehlbachler.bswe.model.Coordinates;
import io.muehlbachler.bswe.service.GeocodingService;
import io.muehlbachler.bswe.service.model.geocoding.GeocodingResult;
import io.muehlbachler.bswe.service.model.geocoding.GeocodingResultEntry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link GeocodingService} interface.
 */
@AllArgsConstructor
@Service
public class GeocodingServiceImpl implements GeocodingService {
  private static final Logger LOG = LoggerFactory.getLogger(GeocodingServiceImpl.class);

  @Autowired
  private final ApiConfiguration apiConfiguration;
  @Autowired
  private final RestTemplate restTemplate;

  @Override
  public Coordinates fetchCoordinates(final String location) {
    if (location == null || location.isEmpty()) {
      return null;
    }

    LOG.info("fetching coordinates for location {}", location);

    try {
      final ResponseEntity<GeocodingResult> result = restTemplate.exchange(apiConfiguration.getGeocoding().getUrl(),
          HttpMethod.GET, null,
          GeocodingResult.class, location);
      if (result == null || result.getStatusCode() != HttpStatus.OK || !result.hasBody()) {
        return null;
      }

      final GeocodingResult results = result.getBody();
      if (results == null || results.getResults() == null || results.getResults().isEmpty()) {
        return null;
      }
      final GeocodingResultEntry entry = results.getResults().get(0);
      LOG.debug("coordinates for location {}: {}", location, entry);
      return new Coordinates(entry.getLongitude(), entry.getLatitude(), entry.getElevation());
    } catch (RestClientException e) {
      LOG.error("failed to parse geocoding response: {}", e.getMessage());
      return null;
    }
  }

  protected Coordinates fetchCoordinatesFallback(final Exception ex) {
    LOG.error("failed to fetch coordinates", ex);
    return null;
  }
}
