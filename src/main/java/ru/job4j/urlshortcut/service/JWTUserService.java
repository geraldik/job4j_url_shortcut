package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JWTUserService implements UserService {

    private final SiteService siteService;

    @Override
    public Optional<Site> getSiteByLogin() {
        var login = SecurityContextHolder.getContext().getAuthentication().getName();
        return siteService.findByLogin(login);
    }
}
