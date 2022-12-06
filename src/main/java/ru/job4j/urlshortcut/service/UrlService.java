package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.UrlLongDto;
import ru.job4j.urlshortcut.dto.UrlShortDto;
import ru.job4j.urlshortcut.dto.UrlStatDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    UrlShortDto save(UrlLongDto urlLongDto);

    Optional<Url> findByLongUrl(String longUrl);

    String generateShortUrl();

    boolean existsByShortUrl(String shortUrl);

    Optional<Url> findByShortUrl(String code);

    void updateCallsCounterByShortUrl(String shortUrl);

    List<UrlStatDto> findAllBySite();

    Optional<Site> getSiteByLogin();
}
