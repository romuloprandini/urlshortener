package com.shortener.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shortener.entity.Url;
import com.shortener.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@JsonTest
public class UrlControllerTest {

    @Mock
    UrlService urlService;

    @InjectMocks
    UrlController urlController;

    JacksonTester<Url> json;

    MockMvc mockMvc;
    Url url;

    @BeforeEach
    void loadUrl() {
        Timestamp now = Timestamp.from(Instant.now());
        url = Url.builder()
            .id(now.getTime())
            .identifier("someRandonIdentifier")
            .protocol("http")
            .host("localhost")
            .port(8080)
            .url("http://localhost:8080/somepath")
            .createdAt(now)
            .build();

        mockMvc = MockMvcBuilders
                .standaloneSetup(urlController)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    void getFullUrl() throws Exception {
        when(urlService.getUrlByIdentifier(ArgumentMatchers.anyString())).thenReturn(url.getUrl());
        doAnswer(i -> null).when(urlService).incrementAccess(ArgumentMatchers.anyString());

        mockMvc.perform(get("/"+url.getIdentifier()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, url.getUrl()));

        verifyZeroInteractions(urlService);
    }

    @Test
    void getFullUrlNoRedirect() throws Exception {
        when(urlService.getByIdentifier(ArgumentMatchers.anyString())).thenReturn(url);
        doAnswer(i -> null).when(urlService).incrementAccess(ArgumentMatchers.anyString());

        mockMvc.perform(get("/"+url.getIdentifier()+"?noRedirect=true"))
                .andExpect(status().isOk())
                .andExpect(content().string(json.write(url).getJson()));

        verifyZeroInteractions(urlService);
    }

    @Test
    void createUrl() throws Exception {
        when(urlService.create(ArgumentMatchers.anyString())).thenReturn(url);

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(url.getUrl()))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/"+url.getIdentifier()));

    }
}
