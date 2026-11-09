package io.muehlbachler.bswe.service.model.geocoding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GeocodingResultEntryTest {
  @Test
  void testNoArgsConstructor() {
    GeocodingResultEntry entry = new GeocodingResultEntry();

    assertNotNull(entry);
    assertEquals(0.0, entry.getLongitude(), 0.001);
    assertEquals(0.0, entry.getLatitude(), 0.001);
    assertEquals(0.0f, entry.getElevation(), 0.001);
  }

  @Test
  void testAllArgsConstructor() {
    double longitude = 16.363449;
    double latitude = 48.210033;
    float elevation = 151.5f;

    GeocodingResultEntry entry = new GeocodingResultEntry(longitude, latitude, elevation);

    assertNotNull(entry);
    assertEquals(longitude, entry.getLongitude(), 0.000001);
    assertEquals(latitude, entry.getLatitude(), 0.000001);
    assertEquals(elevation, entry.getElevation(), 0.001);
  }

  @Test
  void testSettersAndGetters() {
    GeocodingResultEntry entry = new GeocodingResultEntry();

    entry.setLongitude(16.363449);
    entry.setLatitude(48.210033);
    entry.setElevation(151.5f);

    assertEquals(16.363449, entry.getLongitude(), 0.000001);
    assertEquals(48.210033, entry.getLatitude(), 0.000001);
    assertEquals(151.5f, entry.getElevation(), 0.001);
  }

  @Test
  void testToString() {
    GeocodingResultEntry entry = new GeocodingResultEntry(16.363449, 48.210033, 151.5f);
    String toString = entry.toString();

    assertNotNull(toString);
    assertTrue(toString.contains("16.363449"));
    assertTrue(toString.contains("48.210033"));
    assertTrue(toString.contains("151.5"));
  }
}
