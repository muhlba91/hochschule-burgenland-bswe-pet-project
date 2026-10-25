package io.muehlbachler.bswe.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.muehlbachler.bswe.error.ApiException;
import io.muehlbachler.bswe.model.User;
import io.muehlbachler.bswe.repository.UserRepository;
import io.muehlbachler.bswe.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface.
 */
@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
  private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private final UserRepository userRepository;

  @Override
  public boolean exists(final String userId) {
    if (userId == null || userId.isEmpty()) {
      return false;
    }

    return userRepository.existsById(userId);
  }

  @Override
  public List<User> list() {
    final List<User> result = new ArrayList<>();
    userRepository.findAll().forEach(result::add);
    return result;
  }

  @Override
  public User save(final User user) throws ApiException {
    if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
      throw new ApiException(ApiException.ApiExceptionType.NO_DATA);
    }

    try {
      return userRepository.save(user);
    } catch (OptimisticLockingFailureException | IllegalArgumentException | DataIntegrityViolationException e) {
      LOG.error("failed to save user", e);
      throw new ApiException(ApiException.ApiExceptionType.SAVE_ERROR, e);
    }
  }

  @Override
  public boolean delete(final String userId) {
    if (userId == null || userId.isEmpty() || !userRepository.existsById(userId)) {
      return false;
    }

    userRepository.deleteById(userId);
    return true;
  }
}
