package de.merkeg.vsrentbe.confirmation;

public interface ConfirmationProcessor {
    Process processKey();
    void process(Confirmation confirmation, ConfirmationService confirmationService);

}
