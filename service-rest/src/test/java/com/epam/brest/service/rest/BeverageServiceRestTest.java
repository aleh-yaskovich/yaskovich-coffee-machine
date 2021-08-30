package com.epam.brest.service.rest;

import com.epam.brest.model.Beverage;
import com.epam.brest.service.BeverageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class BeverageServiceRestTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BeverageService beverageService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    private String url = "http://localhost:8080/beverages";

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void findAllBeveragesTest() throws Exception {
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                new Beverage(),
                                new Beverage())))
                );

        // when
        List<Beverage> beverages = beverageService.findAllBeverages();

        // then
        mockServer.verify();
        assertNotNull(beverages);
        assertTrue(beverages.size() > 0);
    }

    @Test
    public void findVisibleBeveragesTest() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/client")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                new Beverage(),
                                new Beverage())))
                );

        // when
        List<Beverage> beverages = beverageService.findVisibleBeverages();

        // then
        mockServer.verify();
        assertNotNull(beverages);
        assertTrue(beverages.size() > 0);
    }

    @Test
    public void findIngredientByIdTest() throws Exception {
        // given
        Integer id = 1;
        Beverage beverage = new Beverage();
        beverage.setBeverageId(id);
        beverage.setBeverageTitle("TEST");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url+"/"+id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(beverage))
                );

        // when
        Beverage beverageRes = beverageService.findBeverageById(id);

        // then
        mockServer.verify();
        assertEquals(beverageRes.getBeverageId(), id);
        assertEquals(beverageRes.getBeverageTitle(), beverage.getBeverageTitle());
    }

    @Test
    public void createBeverageTest() throws Exception {
        // given
        Beverage beverage = new Beverage();
        beverage.setBeverageTitle("TEST");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = beverageService.createBeverage(beverage);

        // then
        mockServer.verify();
        assertNotNull(id);
    }


    @Test
    public void updateIngredientTest() throws Exception {
        // given
        Integer id = 1;
        Beverage beverage = new Beverage();
        beverage.setBeverageId(id);
        beverage.setBeverageTitle("TEST");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url+"/"+id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(beverage))
                );

        // when
        int result = beverageService.updateBeverage(beverage);
        Beverage beverageRes = beverageService.findBeverageById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertEquals(beverageRes.getBeverageId(), id);
        assertEquals(beverageRes.getBeverageTitle(), beverage.getBeverageTitle());
    }

    @Test
    public void deleteBeverageTest() throws Exception {
        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(url+"/"+id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = beverageService.deleteBeverage(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }
}