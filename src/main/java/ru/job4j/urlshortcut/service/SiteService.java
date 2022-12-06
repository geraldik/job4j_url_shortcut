package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.SiteDomainDto;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

public interface SiteService {
    SiteDto save(SiteDomainDto siteDomainDto);

    Optional<Site> findByDomain(String domain);

    boolean existsByLogin(String login);

    String generateLogin();

    Optional<Site> findByLogin(String login);
}
