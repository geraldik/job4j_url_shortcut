package ru.job4j.urlshortcut.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomCodeServiceImpl implements RandomCodeService {

    @Override
    public String generateCode(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
