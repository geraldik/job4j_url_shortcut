package ru.job4j.urlshortcut.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UrlStatDto {
    @NonNull
    private String url;
    @NonNull
    private int total;
}
