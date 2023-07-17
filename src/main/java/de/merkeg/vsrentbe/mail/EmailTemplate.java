package de.merkeg.vsrentbe.mail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmailTemplate {

    MAIL_REGISTRATION("template_registration.html", "Registrierung bei VS-Rent");

    private final String path;
    private final String defaultSubject;
}
