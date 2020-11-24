package com.camelrest.excercise;

import com.camelrest.excercise.model.Device;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8081)
class NetworkAPIServiceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    private String serverUrl;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        serverUrl = "http://localhost:" + serverPort + "/network-api/addDevice";
    }

    @Test
    void testAddDevice() throws Exception {
        final Device device = new Device(1, "Device01", "Switch");
        final ResponseEntity responseEntity = addDevice(device);
        assertResponse(responseEntity, 201);
    }

    @Test
    void testRemoveDevice() {
        serverUrl = "http://localhost:" + serverPort + "/network-api/removeDevice/1000";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        final ResponseEntity responseEntity = testRestTemplate.exchange(serverUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertResponse(responseEntity, 200);
    }

    @Test
    void testRemoveNonExistingDevice() {
        serverUrl = "http://localhost:" + serverPort + "/network-api/removeDevice/2000";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        final ResponseEntity responseEntity = testRestTemplate.exchange(serverUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertResponse(responseEntity, 500);
        assertThat(responseEntity.getBody().toString(), CoreMatchers.containsString("400"));
    }

    @Test
    void testDeviceTypeMissing() throws JsonProcessingException {
        final ResponseEntity responseEntity = addDevice(new Device());
        assertResponse(responseEntity, 500);
    }

    private ResponseEntity addDevice(Device device) throws JsonProcessingException {
        final HttpHeaders header = createHeaders();
        final String requestBody = objectMapper.writeValueAsString(device);
        final HttpEntity<String> request = new HttpEntity<String>(requestBody, header);
        return testRestTemplate.postForEntity(serverUrl, request, String.class);
    }

    private void assertResponse(ResponseEntity responseEntity, int status) {
        assertNotNull(responseEntity);
        final String responseBody = (String) responseEntity.getBody();
        assertEquals(status, responseEntity.getStatusCode().value());
        assertNotNull(responseBody);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return header;
    }
}
