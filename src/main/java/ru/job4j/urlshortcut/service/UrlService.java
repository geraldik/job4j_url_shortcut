package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.UrlLongDto;
import ru.job4j.urlshortcut.dto.UrlShortDto;
import ru.job4j.urlshortcut.dto.UrlStatDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final SiteService siteService;

    public UrlShortDto save(UrlLongDto urlLongDto) {
        Optional<Site> byLogin = getSiteByLogin();
        var byLongUrl = this.findByLongUrl(urlLongDto.getUrl());
        var urlShortDto = new UrlShortDto();
        if (byLongUrl.isPresent()) {
            urlShortDto.setCode(byLongUrl.get()
                    .getShortUrl());
            return urlShortDto;
        }
        var shortUrl = generateShortUrl();
        var url = new Url(urlLongDto.getUrl(), shortUrl, byLogin.get());
        var urlSaved = urlRepository.save(url);
        urlShortDto.setCode(urlSaved.getShortUrl());
        return urlShortDto;
    }

    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    private String generateShortUrl() {
        var shortUrl = RandomStringUtils.randomAlphanumeric(8);
        if (existsByShortUrl(shortUrl)) {
            shortUrl = generateShortUrl();
        }
        return shortUrl;
    }

    private boolean existsByShortUrl(String shortUrl) {
        return urlRepository.existsByShortUrl(shortUrl);
    }

    public Optional<Url> findByShortUrl(String code) {
        return urlRepository.findByShortUrl(code);
    }

    public void updateCallsCounterByShortUrl(String shortUrl) {
        urlRepository.updateCallsCounterByShortUrl(shortUrl);
    }

    public List<UrlStatDto> findAllBySite() {
        var optionalSite = getSiteByLogin();
        if (optionalSite.isEmpty()) {
            throw new NoSuchElementException("Please, check Authorization token presence.");
        }
        var site = getSiteByLogin().get();
        return urlRepository.findAllBySite(site).stream()
                .map(url -> new UrlStatDto(url.getLongUrl(), url.getCallsCounter()))
                .toList();
    }

    private Optional<Site> getSiteByLogin() {
        var login = SecurityContextHolder.getContext().getAuthentication().getName();
        return siteService.findByLogin(login);
    }
}
