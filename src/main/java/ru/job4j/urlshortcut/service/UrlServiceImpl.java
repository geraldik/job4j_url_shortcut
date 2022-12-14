package ru.job4j.urlshortcut.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final UserService userService;
    private final RandomCodeService randomCodeService;
    @Value("${codeLength}")
    private int codeLength;

    @Override
    public UrlShortDto save(UrlLongDto urlLongDto) {
        Optional<Site> optionalSite = userService.getSiteByLogin();
        if (optionalSite.isEmpty()) {
            throw new NoSuchElementException("There is no such user with this login");
        }
        var byLongUrl = this.findByLongUrl(urlLongDto.getUrl());
        var urlShortDto = new UrlShortDto();
        if (byLongUrl.isPresent()) {
            urlShortDto.setCode(byLongUrl.get()
                    .getShortUrl());
            return urlShortDto;
        }
        var shortUrl = generateShortUrl();
        var url = new Url(urlLongDto.getUrl(), shortUrl, optionalSite.get());
        var urlSaved = urlRepository.save(url);
        urlShortDto.setCode(urlSaved.getShortUrl());
        return urlShortDto;
    }

    @Override
    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    @Override
    public String generateShortUrl() {
        var shortUrl = randomCodeService.generateCode(codeLength);
        if (existsByShortUrl(shortUrl)) {
            shortUrl = generateShortUrl();
        }
        return shortUrl;
    }

    @Override
    public boolean existsByShortUrl(String shortUrl) {
        return urlRepository.existsByShortUrl(shortUrl);
    }

    @Override
    public Optional<Url> findByShortUrl(String code) {
        return urlRepository.findByShortUrl(code);
    }

    @Override
    public void updateCallsCounterByShortUrl(String shortUrl) {
        urlRepository.updateCallsCounterByShortUrl(shortUrl);
    }

    @Override
    public List<UrlStatDto> findAllBySite() {
        var optionalSite = userService.getSiteByLogin();
        if (optionalSite.isEmpty()) {
            throw new NoSuchElementException("There is no such user with this login");
        }
        var site = optionalSite.get();
        return urlRepository.findAllBySite(site).stream()
                .map(url -> new UrlStatDto(url.getLongUrl(), url.getCallsCounter()))
                .toList();
    }

}
