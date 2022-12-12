package ru.job4j.urlshortcut.service;

public interface RandomCodeService {

    /**
     * Generate a random string whose length is the number of characters specified.
     * Characters will be chosen from the set of Latin alphabetic characters (a-z, A-Z) and the digits 0-9
     * @param length – the length of random string to create
     * @return generated code as String
     */
    String generateCode(int length);
}
