package io.muehlbachler.bswe.controller;

import io.muehlbachler.bswe.model.Coordinates;
import io.muehlbachler.bswe.service.GeocodingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to expose geocoding endpoints.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/geocoding")
@CrossOrigin
public class GeocodingController {
  @Autowired
  private final GeocodingService geocodingService;

  /**
   * Fetches the coordinates for a given location.
   *
   * @param location the location to fetch the coordinates for
   * @return the coordinates of the location
   */
  @GetMapping("/")
  public ResponseEntity<Coordinates> fetch(@RequestParam final String location) {
    final Coordinates coordinates = geocodingService.fetchCoordinates(location);
    if (coordinates == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(coordinates, HttpStatus.OK);
  }
}
