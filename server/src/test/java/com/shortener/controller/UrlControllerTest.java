package com.shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortener.entity.TopHost;
import com.shortener.entity.Url;
import com.shortener.service.UrlService;
import com.shortener.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

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

    JacksonTester<Url> jsonUrl;
    JacksonTester<TopHost> jsonTopHost;
    MockMvc mockMvc;
    Url url;

    @BeforeEach
    void configureTest() {
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
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
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

    @Test
    void getFullUrl() throws Exception {
        when(urlService.exists(ArgumentMatchers.anyString())).thenReturn(true);
        when(urlService.getUrlByIdentifier(ArgumentMatchers.anyString())).thenReturn(url.getUrl());
        doAnswer(i -> null).when(urlService).incrementAccess(ArgumentMatchers.anyString());

        mockMvc.perform(get("/"+url.getIdentifier()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, url.getUrl()));

        verifyZeroInteractions(urlService);
    }

    @Test
    void getFullUrlNoRedirect() throws Exception {
        when(urlService.exists(ArgumentMatchers.anyString())).thenReturn(true);
        when(urlService.getByIdentifier(ArgumentMatchers.anyString())).thenReturn(url);
        doAnswer(i -> null).when(urlService).incrementAccess(ArgumentMatchers.anyString());

        mockMvc.perform(get("/"+url.getIdentifier()+"?noRedirect=true"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonUrl.write(url).getJson()));

        verifyZeroInteractions(urlService);
    }

    @Test
    void getLatestUrl() throws Exception {
        when(urlService.findLatestUrlCreated()).thenReturn(url);

        mockMvc.perform(get("/latest"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonUrl.write(url).getJson()));

        verifyZeroInteractions(urlService);
    }

    @Test
    void getTopUrl() throws Exception {
        when(urlService.findTopUrl(ArgumentMatchers.any())).thenReturn(new PageImpl<>(Arrays.asList(url)));

        mockMvc.perform(get("/top?type=url"))
                .andExpect(status().isOk())
                .andExpect(content().string("["+jsonUrl.write(url).getJson()+"]"));
                //.andExpect(header().string(PaginationUtil.X_TOTAL_COUNT, "1"));

        verifyZeroInteractions(urlService);
    }

    @Test
    void getTopHosts() throws Exception {
        TopHost topHost = new TopHost(url.getHost(), 1);
        when(urlService.findTopHost(ArgumentMatchers.any())).thenReturn(new PageImpl<>(Arrays.asList(topHost)));

        mockMvc.perform(get("/top?type=host"))
                .andExpect(status().isOk())
                .andExpect(content().string("["+jsonTopHost.write(topHost).getJson()+"]"));
                //.andExpect(header().stringValues(PaginationUtil.X_TOTAL_COUNT, "1"));

        verifyZeroInteractions(urlService);
    }

}
