package io.muehlbachler.bswe.controller;

import java.util.List;

import io.muehlbachler.bswe.controller.dto.UserCreateDto;
import io.muehlbachler.bswe.controller.dto.UserListDto;
import io.muehlbachler.bswe.error.ApiException;
import io.muehlbachler.bswe.model.User;
import io.muehlbachler.bswe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to expose user endpoints.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
  @Autowired
  private final UserService userService;

  /**
   * Creates a new user.
   *
   * @param userDto the user to create
   * @return the created user
   */
  @PostMapping("/")
  public ResponseEntity<User> create(@RequestBody final UserCreateDto userDto) {
    if (userDto == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    final User user = new User();
    user.setUsername(userDto.getUsername());

    try {
      final User createdUser = userService.save(user);
      if (createdUser == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    } catch (final ApiException e) {
      return new ResponseEntity<>(e.getHttpStatus());
    }
  }

  /**
   * Lists all users.
   *
   * @return the list of users
   */
  @GetMapping("/")
  public ResponseEntity<UserListDto> list() {
    final List<User> users = userService.list();
    if (users == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(new UserListDto(users), HttpStatus.OK);
  }

  /**
   * Deletes a user.
   *
   * @param id the user id
   * @return the status of the deletion
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable final String id) {
    return new ResponseEntity<>(
        userService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
  }
}
