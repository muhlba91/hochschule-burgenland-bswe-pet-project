package io.muehlbachler.bswe.service;

import java.util.List;

import io.muehlbachler.bswe.error.ApiException;
import io.muehlbachler.bswe.model.User;

/**
 * A service to handle all user related actions.
 */
public interface UserService {
  /**
   * Checks if a user with the given id exists.
   *
   * @param userId the user id to check
   * @return true if the user exists, false otherwise
   */
  boolean exists(String userId);

  /**
   * Lists all users.
   *
   * @return a list of all users
   */
  List<User> list();

  /**
   * Saves a user.
   *
   * @param user the user to save
   * @return the saved user
   * @throws ApiException if the user could not be saved
   */
  User save(User user) throws ApiException;

  /**
   * Deletes a user by its id.
   *
   * @param userId the user id
   * @return true if the user was deleted, false otherwise
   */
  boolean delete(String userId);
}
