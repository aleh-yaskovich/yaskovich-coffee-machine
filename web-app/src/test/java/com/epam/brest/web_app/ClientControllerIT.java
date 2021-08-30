package com.epam.brest.web_app;

//import com.epam.brest.model.Beverage;
//import com.epam.brest.model.Client;
//import com.epam.brest.service.BeverageService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = WebAppTestConfig.class)
class ClientControllerIT {

//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private BeverageService beverageService;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//
//    @Test
//    void showClientPageTest() throws Exception {
//        mockMvc.perform(get("/client")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(view().name("client"))
//                .andExpect(model().attribute("newBeverage", new Beverage()))
//                .andExpect(model().attribute("credit", "0.00"))
//                .andExpect(model().attribute("totalPrice", "0.00"));
//    }
//
//    @Test
//    void clientCreditTest() throws Exception {
//        mockMvc.perform(post("/client")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/client"));
//    }
//
//    @Test
//    void addBeverage_and_deleteSelectedBeverage_Test() throws Exception {
//        List<Beverage> beverages = beverageService.findVisibleBeverages();
//        mockMvc.perform(post("/client/1")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/client"));
//        mockMvc.perform(post("/client/2")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/client"));
//        mockMvc.perform(get("/client")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(view().name("client"))
//                .andExpect(model().attribute("totalPrice", String.format("%.2f",
//                        (beverages.get(0).getBeveragePrice()+beverages.get(1).getBeveragePrice()))));
//        mockMvc.perform(get("/delete/1")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/client"));
//        mockMvc.perform(get("/client")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(view().name("client"))
//                .andExpect(model().attribute("totalPrice", String.format("%.2f",beverages.get(1).getBeveragePrice())));
//    }
//
//    @Test
//    void makePaymentTest() throws Exception {
//        mockMvc.perform(get("/order")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/client"));
//    }
}