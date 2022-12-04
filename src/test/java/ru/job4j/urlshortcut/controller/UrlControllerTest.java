package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;
import ru.job4j.urlshortcut.dto.UrlLongDto;
import ru.job4j.urlshortcut.dto.UrlShortDto;
import ru.job4j.urlshortcut.dto.UrlStatDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.service.SiteService;
import ru.job4j.urlshortcut.service.UrlService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    SiteService siteService;

    @MockBean
    private UrlService urlService;

    @Test
    @WithMockUser()
    public void whenConvertUrlThenOk() throws Exception {
        var urlLongDto = new UrlLongDto();
        urlLongDto.setUrl("longUrl");
        var urlShortDto = new UrlShortDto();
        urlShortDto.setCode("shortUrl");
        Mockito.when(urlService.save(urlLongDto)).thenReturn(urlShortDto);
        this.mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("url", "longUrl"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("shortUrl"));
    }

    @Test
    public void whenRedirectInvalidCodeThenBadRequest() throws Exception {
        var shortUrl = "shortUrl";
        Mockito.when(urlService.findByShortUrl(shortUrl)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/redirect/" + shortUrl))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenRedirectThenGetLocation() throws Exception {
        var shortUrl = "shortUrl";
        var longUrl = "longUrl";
        var site = new Site("domain", "login", "pass");
        var url = new Url(longUrl, shortUrl, site);
        Mockito.when(urlService.findByShortUrl(shortUrl)).thenReturn(Optional.of(url));
        this.mockMvc.perform(get("/redirect/" + shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Is.is("longUrl")));
    }

    @Test
    @WithMockUser
    public void whenLoadStatisticThenOk() throws Exception {
        var site = new Site("domain", "login", "pass");
        Mockito.when(siteService.findByLogin("user")).thenReturn(Optional.of(site));
        var urlStatDto1 = new UrlStatDto("url1", 1);
        var urlStatDto2 = new UrlStatDto("url2", 2);
        var urlStatDto3 = new UrlStatDto("url3", 3);
        Mockito.when(urlService.findAllBySite()).thenReturn(List.of(urlStatDto1, urlStatDto2, urlStatDto3));
        this.mockMvc.perform(get("/statistic"))
                .andExpect(jsonPath("$.[0].url", Is.is(urlStatDto1.getUrl())))
                .andExpect(jsonPath("$.[0].total", Is.is(urlStatDto1.getTotal())))

                .andExpect(jsonPath("$.[1].url", Is.is(urlStatDto2.getUrl())))
                .andExpect(jsonPath("$.[1].total", Is.is(urlStatDto2.getTotal())))

                .andExpect(jsonPath("$.[2].url", Is.is(urlStatDto3.getUrl())))
                .andExpect(jsonPath("$.[2].total", Is.is(urlStatDto3.getTotal())));
    }
}

