package io.muehlbachler.bswe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import com.google.gson.Gson;
import io.muehlbachler.bswe.controller.dto.UserCreateDto;
import io.muehlbachler.bswe.controller.dto.UserListDto;
import io.muehlbachler.bswe.model.User;
import io.muehlbachler.bswe.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private UserRepository repository;

  private User user;
  private UserCreateDto userDto;

  @BeforeEach
  public void setUp() {
    repository.deleteAll();

    user = new User();
    user.setUsername("username");

    userDto = new UserCreateDto();
    userDto.setUsername("username");
  }

  @AfterEach
  public void tearDown() {
    repository.deleteAll();
  }

  @Test
  public void testList() throws Exception {
    repository.save(user);

    MvcResult result = mvc
        .perform(MockMvcRequestBuilders.get("/api/user/"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    UserListDto usersResult = new Gson().fromJson(result.getResponse().getContentAsString(),
        UserListDto.class);
    assertNotNull(usersResult);
    assertEquals(1, usersResult.getUsers().size());
    assertEquals(user.getUsername(), usersResult.getUsers().get(0).getUsername());
  }

  @Test
  public void testListEmpty() throws Exception {
    MvcResult result = mvc
        .perform(MockMvcRequestBuilders.get("/api/user/"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    UserListDto usersResult = new Gson().fromJson(result.getResponse().getContentAsString(),
        UserListDto.class);
    assertNotNull(usersResult);
    assertTrue(usersResult.getUsers().isEmpty());
  }

  @Test
  public void testCreate() throws Exception {
    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new Gson().toJson(userDto)))
        .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    User user = new Gson().fromJson(result.getResponse().getContentAsString(),
        User.class);
    assertNotNull(user);
    assertEquals(userDto.getUsername(), user.getUsername());
    assertTrue(repository.existsById(user.getId()));
  }

  @Test
  public void testCreateEmptyUsername() throws Exception {
    userDto.setUsername("");

    mvc.perform(MockMvcRequestBuilders.post("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new Gson().toJson(
            userDto)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    assertEquals(0, repository.count());
  }

  @Test
  public void testCreateNull() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new Gson().toJson(
            null)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    assertEquals(0, repository.count());
  }

  @Test
  public void testCreateNullUsername() throws Exception {
    userDto.setUsername(null);

    mvc.perform(MockMvcRequestBuilders.post("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new Gson().toJson(
            userDto)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    assertEquals(0, repository.count());
  }

  @Test
  public void testDelete() throws Exception {
    repository.save(user);

    mvc.perform(MockMvcRequestBuilders.delete("/api/user/" + user.getId()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    assertEquals(0, repository.count());
  }

  @Test
  public void testDeleteFails() throws Exception {
    repository.save(user);

    mvc.perform(MockMvcRequestBuilders.delete("/api/user/" + UUID.randomUUID().toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    assertEquals(1, repository.count());
  }
}
