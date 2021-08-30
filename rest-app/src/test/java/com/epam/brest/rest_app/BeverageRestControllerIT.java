package com.epam.brest.rest_app;

import com.epam.brest.model.Beverage;
import com.epam.brest.rest_app.exception.BeverageNotFoundAdvice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class BeverageRestControllerIT {

    public static final String BEVERAGES_ENDPOINT = "/beverages";

    @Autowired
    private BeverageRestController beverageRestController;

    @Autowired
    private BeverageNotFoundAdvice beverageNotFoundAdvice;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockBeverageService beverageService = new MockBeverageService();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(beverageRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(beverageNotFoundAdvice)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void beveragesTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertNotNull(beverages);
        assertTrue(beverages.size() > 0);
    }

    @Test
    void beverageTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertNotNull(beverages);
        assertTrue(beverages.size() > 0);

        Beverage beverage = beverages.get(0);
        Beverage testBeverage = beverageService.findBeverageById(beverage.getBeverageId());

        assertEquals(beverage, testBeverage);
        assertEquals(beverage.getBeverageTitle(), testBeverage.getBeverageTitle());
    }

    @Test
    void createTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertTrue(beverages.size() > 0);
        int listSize = beverages.size();

        Beverage beverage = new Beverage();
        beverage.setBeverageTitle("TEST");
        Integer id = beverageService.createBeverage(beverage);

        List<Beverage> testBeverages = beverageService.findAllBeverages();
        assertTrue(testBeverages.size() == (listSize + 1));
    }

    @Test
    void updateTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertTrue(beverages.size() > 0);

        Beverage beverage = beverages.get(0);
        beverage.setBeverageTitle("UPDATE_TITLE");
        beverageService.updateBeverage(beverage);

        List<Beverage> testBeverages = beverageService.findAllBeverages();
        assertEquals("UPDATE_TITLE", testBeverages.get(0).getBeverageTitle());
    }

    @Test
    void deleteTest() throws Exception {
        List<Beverage> beverages = beverageService.findAllBeverages();
        assertTrue(beverages.size() > 0);
        Integer res = beverageService.deleteBeverage(beverages.size() - 1);
        assertTrue(res > 0);
    }

    /////////////////////////////////////////////////////////////////////////////////

    private class MockBeverageService{

        List<Beverage> findAllBeverages() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(BEVERAGES_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Beverage>>() {});
        };

        Beverage findBeverageById(Integer beverageId) throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(BEVERAGES_ENDPOINT + "/" + beverageId)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Beverage.class);
        };

        Integer createBeverage(Beverage beverage) throws Exception {
            String json = objectMapper.writeValueAsString(beverage);
            MockHttpServletResponse response =
                    mockMvc.perform(post(BEVERAGES_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        };

        Integer updateBeverage(Beverage beverage) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put(BEVERAGES_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(beverage))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        };

        Integer deleteBeverage(Integer beverageId) throws Exception {
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(BEVERAGES_ENDPOINT).append("/")
                                            .append(beverageId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        };
    }
}