package com.db6n.db6nrenting.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        // TODO: Regexto validete email
        return true;
    }
}
