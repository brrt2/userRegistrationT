package com.task.userRegistration.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private static final String VALID_PASSWORD = "Administrator123";
    private static final String INVALID_PASSWORD = "letmein";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldDetectErrorsWhenIncorrectInputIsProvided() throws Exception {
        this.mockMvc
                .perform(
                        post("/users")
                                .param("username", "bartosz")
                                .param("password", INVALID_PASSWORD)

                )
                .andExpect(model().hasErrors())
//                .andExpect(model().attributeHasErrors("password"))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldRedirectToUserCreatedPageWhenValidationIsSuccessful() throws Exception {
        this.mockMvc
                .perform(
                        post("/users")
                                .param("username", "bartosz")
                                .param("password", VALID_PASSWORD)
                        
                )
                .andExpect(model().hasNoErrors())
                .andExpect(status().is2xxSuccessful());
    }
}
