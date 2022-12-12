package ru.job4j.urlshortcut.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.SiteDomainDto;
import ru.job4j.urlshortcut.dto.SiteDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SiteServiceImpl implements SiteService {
    private final SiteRepository repository;
    private final PasswordEncoder delegatingPasswordEncoder;
    private final RandomCodeService randomCodeService;
    @Value("${loginLength}")
    private int loginLength;
    @Value("${passwordLength}")
    private int passwordLength;

    @Override
    public SiteDto save(SiteDomainDto siteDomainDto) {
        var domain = siteDomainDto.getSite();
        var site = findByDomain(domain);
        if (site.isPresent()) {
            return new SiteDto(false, site.get().getLogin(), site.get().getPassword());
        }
        var login = generateLogin();
        var password = randomCodeService.generateCode(passwordLength);
        var result = new SiteDto(true, login, password);
        repository.save(
                new Site(domain, login, delegatingPasswordEncoder.encode(password)));
        return result;
    }

    @Override
    public Optional<Site> findByDomain(String domain) {
        return repository.findByDomain(domain);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    private String generateLogin() {
        var login = randomCodeService.generateCode(loginLength);
        if (existsByLogin(login)) {
            login = generateLogin();
        }
        return login;
    }

    @Override
    public Optional<Site> findByLogin(String login) {
        return repository.findByLogin(login);
    }
}
