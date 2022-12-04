package ru.job4j.urlshortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SiteDomainDto {
    @NotBlank(message = "Site domain must not be empty")
    private String site;
}
