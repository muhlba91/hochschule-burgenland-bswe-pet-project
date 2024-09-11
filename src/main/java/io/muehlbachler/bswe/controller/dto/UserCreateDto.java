package io.muehlbachler.bswe.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO to create a user.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
  private String username;
}
