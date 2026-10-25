package io.muehlbachler.bswe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import io.muehlbachler.bswe.error.ApiException;
import io.muehlbachler.bswe.model.User;
import io.muehlbachler.bswe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  @Mock
  private UserRepository userRepository;

  private UserServiceImpl userService;

  @BeforeEach
  public void setUp() {
    userService = new UserServiceImpl(userRepository);
  }

  @Test
  public void testExistsWithValidUserId() {
    when(userRepository.existsById("user1")).thenReturn(true);

    assertTrue(userService.exists("user1"));
    verify(userRepository).existsById("user1");
  }

  @Test
  public void testExistsWithInvalidUserId() {
    assertFalse(userService.exists(null));
    assertFalse(userService.exists(""));
    verify(userRepository, never()).existsById(any());
  }

  @Test
  public void testExistsWithNonExistentUserId() {
    when(userRepository.existsById("nonexistent")).thenReturn(false);

    assertFalse(userService.exists("nonexistent"));
    verify(userRepository).existsById("nonexistent");
  }

  @Test
  public void testList() {
    User user1 = new User("user1", "User One");
    User user2 = new User("user2", "User Two");
    List<User> expectedUsers = Arrays.asList(user1, user2);
    when(userRepository.findAll()).thenReturn(expectedUsers);

    List<User> actualUsers = userService.list();

    assertEquals(expectedUsers, actualUsers);
    verify(userRepository).findAll();
  }

  @Test
  public void testListEmpty() {
    when(userRepository.findAll()).thenReturn(List.of());

    List<User> users = userService.list();

    assertTrue(users.isEmpty());
    verify(userRepository).findAll();
  }

  @Test
  public void testSave() throws ApiException {
    User user = new User("user1", "User One");
    when(userRepository.save(user)).thenReturn(user);

    User savedUser = userService.save(user);

    assertEquals(user, savedUser);
    verify(userRepository).save(user);
  }

  @Test
  public void testSaveNull() {
    assertThrows(ApiException.class, () -> userService.save(null));
    verify(userRepository, never()).save(any());
  }

  @Test
  public void testSaveEmptyUsername() {
    User user = new User("id1", "");
    assertThrows(ApiException.class, () -> userService.save(user));
    verify(userRepository, never()).save(any());
  }

  @Test
  public void testSaveOptimisticLockingFailure() {
    User user = new User("user1", "User One");
    when(userRepository.save(user)).thenThrow(OptimisticLockingFailureException.class);

    ApiException exception = assertThrows(ApiException.class, () -> userService.save(user));
    assertEquals(ApiException.ApiExceptionType.SAVE_ERROR, exception.getType());
    verify(userRepository).save(user);
  }

  @Test
  public void testSaveDataIntegrityViolation() {
    User user = new User("user1", "User One");
    when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

    ApiException exception = assertThrows(ApiException.class, () -> userService.save(user));
    assertEquals(ApiException.ApiExceptionType.SAVE_ERROR, exception.getType());
    verify(userRepository).save(user);
  }

  @Test
  public void testDelete() {
    when(userRepository.existsById("user1")).thenReturn(true);

    assertTrue(userService.delete("user1"));
    verify(userRepository).deleteById("user1");
  }

  @Test
  public void testDeleteNonExistent() {
    when(userRepository.existsById("nonexistent")).thenReturn(false);

    assertFalse(userService.delete("nonexistent"));
    verify(userRepository, never()).deleteById(any());
  }

  @Test
  public void testDeleteNull() {
    assertFalse(userService.delete(null));
    verify(userRepository, never()).deleteById(any());
  }

  @Test
  public void testDeleteEmpty() {
    assertFalse(userService.delete(""));
    verify(userRepository, never()).deleteById(any());
  }
}
