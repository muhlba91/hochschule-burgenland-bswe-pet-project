package io.muehlbachler.bswe.repository;

import io.muehlbachler.bswe.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD Repository for the {@link User} entity.
 */
public interface UserRepository extends CrudRepository<User, String> {
}
