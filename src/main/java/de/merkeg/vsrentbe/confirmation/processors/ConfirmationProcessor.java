package de.merkeg.vsrentbe.confirmation.processors;

import de.merkeg.vsrentbe.confirmation.Confirmation;
import de.merkeg.vsrentbe.confirmation.ConfirmationService;
import de.merkeg.vsrentbe.confirmation.Process;

public interface ConfirmationProcessor {
    Process processKey();
    void process(Confirmation confirmation, ConfirmationService confirmationService, ConfirmationResult result);
    void preData(Confirmation confirmation, ConfirmationService confirmationService);
}
