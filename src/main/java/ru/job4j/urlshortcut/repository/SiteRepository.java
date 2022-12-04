package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

@Repository
public interface SiteRepository extends CrudRepository<Site, Integer> {
    Optional<Site> findByDomain(String name);

    boolean existsByLogin(String login);

    Optional<Site> findByLogin(String login);
}
