package io.muehlbachler.bswe.controller.dto;

import java.util.List;

import io.muehlbachler.bswe.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO to list users.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {
  private List<User> users;
}
