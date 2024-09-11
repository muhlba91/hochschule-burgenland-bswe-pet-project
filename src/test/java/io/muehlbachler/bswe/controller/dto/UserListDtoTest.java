package io.muehlbachler.bswe.controller.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import io.muehlbachler.bswe.model.User;
import org.junit.jupiter.api.Test;

class UserListDtoTest {
  @Test
  void testNoArgsConstructor() {
    final UserListDto dto = new UserListDto();
    assertNotNull(dto);
  }

  @Test
  void testAllArgsConstructor() {
    final List<User> users = new ArrayList<>();
    final UserListDto dto = new UserListDto(users);
    assertNotNull(dto);
    assertEquals(users, dto.getUsers());
  }

  @Test
  void testSetters() {
    final List<User> users = new ArrayList<>();
    final UserListDto dto = new UserListDto();
    dto.setUsers(users);

    assertEquals(users, dto.getUsers());
  }
}
