package io.muehlbachler.bswe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.muehlbachler.bswe.controller.dto.UserCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "user-service")
public class UserControllerContractTest {
    private static final String CONSUMER_NAME = "bswe-client";
    private static final String TEST_USER_ID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
    private static final String TEST_USERNAME = "johndoe";

    private final Gson gson = new Gson();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Pact(consumer = CONSUMER_NAME)
    public V4Pact createUserPact(final PactBuilder builder) {
        final PactDslJsonBody requestBody = new PactDslJsonBody()
                .stringType("username", TEST_USERNAME);
        final PactDslJsonBody responseBody = new PactDslJsonBody()
                .uuid("id", TEST_USER_ID)
                .stringType("username", TEST_USERNAME);

        return builder
                .usingLegacyDsl()
                .given("no user with username johndoe exists")
                .uponReceiving("a request to create a new user")
                .path("/api/user/")
                .method("POST")
                .headers("Content-Type", "application/json")
                .body(requestBody)
                .willRespondWith()
                .status(201)
                .headers(Map.of("Content-Type", "application/json"))
                .body(responseBody)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createUserPact")
    void testCreateUser(final MockServer mockServer) throws Exception {
        final UserCreateDto createDto = new UserCreateDto();
        createDto.setUsername(TEST_USERNAME);

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(mockServer.getUrl() + "/api/user/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(createDto)))
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        final JsonObject body = gson.fromJson(response.body(), JsonObject.class);
        assertEquals(TEST_USER_ID, body.get("id").getAsString());
        assertEquals(TEST_USERNAME, body.get("username").getAsString());
    }

    @Pact(consumer = CONSUMER_NAME)
    public V4Pact listUsersPact(final PactBuilder builder) {
        final PactDslJsonBody user = new PactDslJsonBody()
                .uuid("id", TEST_USER_ID)
                .stringType("username", TEST_USERNAME);
        final PactDslJsonBody responseBody = new PactDslJsonBody()
                .minArrayLike("users", 1, user);

        return builder
                .usingLegacyDsl()
                .given("a user with username johndoe exists")
                .uponReceiving("a request to list all users")
                .path("/api/user/")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(responseBody)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "listUsersPact")
    void testListUsers(final MockServer mockServer) throws Exception {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(mockServer.getUrl() + "/api/user/"))
                .GET()
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        final JsonObject body = gson.fromJson(response.body(), JsonObject.class);
        final JsonArray users = body.getAsJsonArray("users");
        assertTrue(users.size() >= 1);
        assertEquals(TEST_USER_ID, users.get(0).getAsJsonObject().get("id").getAsString());
        assertEquals(TEST_USERNAME, users.get(0).getAsJsonObject().get("username").getAsString());
    }
}
