package com.staselko.HelpDesk.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.staselko.HelpDesk.model.entiti.Category;
import com.staselko.HelpDesk.model.enums.Urgency;
import com.staselko.HelpDesk.security.JwtRequest;
import com.staselko.HelpDesk.service.CategoriesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriesService categoriesService;

    @Test
    void authentication() throws Exception {
        JwtRequest request = new JwtRequest("m1@yopmail.com", "Mm123*");
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(request.getUsername()));
    }

    @Test
    @WithMockUser(username = "m1@yopmail.com")
    void getCategories() throws Exception {
        List<Category> categories = categoriesService.getAllCategories();
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(categories.size())))
                .andExpect(jsonPath("$[0].name", Matchers.is(categories.get(0).getName())));
    }

    @Test
    @WithMockUser(username = "m1@yopmail.com")
    void getUrgencies() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(Urgency.CRITICAL.name());
        list.add(Urgency.HIGH.name());
        list.add(Urgency.AVERAGE.name());
        list.add(Urgency.LOW.name());

        MvcResult result = mockMvc.perform(get("/urgencies"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<String> urgencies = JsonPath.read(json, "$[*]");
        Assertions.assertTrue(list.containsAll(urgencies));
        Assertions.assertEquals(list.size(), urgencies.size());
    }































}
