package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

public interface UserService {
    Optional<Site> getSiteByLogin();
}
