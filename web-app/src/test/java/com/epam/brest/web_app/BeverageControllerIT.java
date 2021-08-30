package com.epam.brest.web_app;

//import com.epam.brest.model.Beverage;
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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = WebAppTestConfig.class)
class BeverageControllerIT {

//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//
//    @Test
//    void showAllBeveragesTest() throws Exception {
//        mockMvc.perform(get("/beverages")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(view().name("beverages"));
//    }
//
//    @Test
//    void showCreateNewBeveragePageTest() throws Exception {
//        mockMvc.perform(get("/beverage")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(view().name("beverage"))
//                .andExpect(model().attribute("beverage", new Beverage()));
//    }
//
//    @Test
//    void createNewBeverageTest() throws Exception {
//        mockMvc.perform(post("/beverage")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/beverages"))
//                .andExpect(model().attribute("message", "The beverage created successfully!"));
//    }
//
//    @Test
//    void showUpdateBeveragePageTest() throws Exception {
//        mockMvc.perform(get("/beverage/1")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(view().name("beverage"));
//    }
//
//    @Test
//    void showUpdateBeveragePageTest_withNonexistentId() throws Exception {
//        mockMvc.perform(get("/beverage/100")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/beverages"));
//    }
//
////    @Test
////    void updateBeverageTest() throws Exception {
////        mockMvc.perform(post("/beverage/1")).andDo(print())
////                .andExpect(MockMvcResultMatchers.status().is(302))
////                .andExpect(view().name("redirect:/beverages"))
////                .andExpect(model().attribute("message", "The beverage updated successfully!"));
////    }
//
//    @Test
//    void deleteBeverageTest() throws Exception {
//        mockMvc.perform(get("/beverage/3/delete")).andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is(302))
//                .andExpect(view().name("redirect:/beverages"))
//                .andExpect(model().attribute("message", "The beverage deleted successfully!"));
//    }
}