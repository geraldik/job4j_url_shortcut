package ru.job4j.urlshortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UrlLongDto {
    @NotEmpty(message = "Url must not be empty")
    private String url;
}
