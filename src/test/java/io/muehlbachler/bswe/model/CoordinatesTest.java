package io.muehlbachler.bswe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CoordinatesTest {
  @Test
  void testDefaultConstructor() {
    Coordinates coordinates = new Coordinates();
    assertEquals(0.0, coordinates.getLongitude());
    assertEquals(0.0, coordinates.getLatitude());
    assertEquals(0.0f, coordinates.getElevation());
  }

  @Test
  void testParameterizedConstructor() {
    double longitude = 15.5;
    double latitude = 48.2;
    float elevation = 300.5f;

    Coordinates coordinates = new Coordinates(longitude, latitude, elevation);

    assertEquals(longitude, coordinates.getLongitude());
    assertEquals(latitude, coordinates.getLatitude());
    assertEquals(elevation, coordinates.getElevation());
  }

  @Test
  void testSettersAndGetters() {
    Coordinates coordinates = new Coordinates();

    coordinates.setLongitude(15.5);
    coordinates.setLatitude(48.2);
    coordinates.setElevation(300.5f);

    assertEquals(15.5, coordinates.getLongitude());
    assertEquals(48.2, coordinates.getLatitude());
    assertEquals(300.5f, coordinates.getElevation());
  }

  @Test
  void testEqualsAndHashCode() {
    Coordinates coords1 = new Coordinates(15.5, 48.2, 300.5f);
    Coordinates coords2 = new Coordinates(15.5, 48.2, 300.5f);
    Coordinates coords3 = new Coordinates(15.6, 48.2, 300.5f);

    assertEquals(coords1, coords2);
    assertNotEquals(coords1, coords3);
    assertEquals(coords1.hashCode(), coords2.hashCode());
    assertNotEquals(coords1.hashCode(), coords3.hashCode());
  }

  @Test
  void testToString() {
    Coordinates coordinates = new Coordinates(15.5, 48.2, 300.5f);
    String toString = coordinates.toString();

    assertTrue(toString.contains("15.5"));
    assertTrue(toString.contains("48.2"));
    assertTrue(toString.contains("300.5"));
  }
}
