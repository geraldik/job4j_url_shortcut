package ru.job4j.urlshortcut.model;

import lombok.*;

import javax.persistence.*;

/**
 * The Url class represent long-short url compliance.
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "url")
public class Url {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The value is used for counting number of redirection requests.
     */
    @Column(name = "calls_counter")
    private int callsCounter;
    private String longUrl;
    private String shortUrl;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "site_id")
    private Site site;

    public Url(String longUrl, String shortUrl, Site site) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.site = site;
    }
}
