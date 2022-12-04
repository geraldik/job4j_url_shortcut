package ru.job4j.urlshortcut.model;

import lombok.*;

import javax.persistence.*;

/**
 * The Site class represent model of user url_shortcut service.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "site")
public class Site {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The value is used for domain storage coming in a registration request.
     */
    private String domain;

    /**
     * The value is used for generated login storage.
     */
    private String login;

    /**
     * The value is used for generated password storage.
     */
    private String password;

    public Site(String domain, String login, String password) {
        this.domain = domain;
        this.login = login;
        this.password = password;
    }
}
