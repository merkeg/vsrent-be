package de.merkeg.vsrentbe.confirmation.processors;

import de.merkeg.vsrentbe.confirmation.*;
import de.merkeg.vsrentbe.confirmation.Process;
import de.merkeg.vsrentbe.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailRegistrationProcessor implements ConfirmationProcessor {

   private final UserRepository repository;

    @Override
    public Process processKey() {
        return Process.REGISTRATION_EMAIL;
    }

    @Override
    public void process(Confirmation confirmation, ConfirmationService confirmationService, ConfirmationResult result) {
        if(result == ConfirmationResult.ACCEPT) {
            confirmation.getTargetUser().setEmailVerified(true);
            repository.save(confirmation.getTargetUser());
            confirmationService.userDeleteAllFromProcess(Process.REGISTRATION_EMAIL, confirmation.getTargetUser());
        }


    }

    @Override
    public void preData(Confirmation confirmation, ConfirmationService confirmationService) {

    }
}
