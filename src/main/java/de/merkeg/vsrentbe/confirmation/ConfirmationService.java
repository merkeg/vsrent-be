package de.merkeg.vsrentbe.confirmation;

import de.merkeg.vsrentbe.confirmation.processors.ConfirmationProcessors;
import de.merkeg.vsrentbe.confirmation.processors.ConfirmationResult;
import de.merkeg.vsrentbe.exception.ConfirmationNotFoundException;
import de.merkeg.vsrentbe.exception.InvalidSecretTokenException;
import de.merkeg.vsrentbe.user.User;
import de.merkeg.vsrentbe.util.Base58;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationService {
    private final ConfirmationRepository confirmationRepository;
    private final ConfirmationProcessors processors;

    @Value("${baseurl}")
    private String baseUrl;

    public Confirmation createEmailConfirmation(User user) {
        Confirmation confirmation = Confirmation.builder()
                .id(Base58.uuid58())
                .targetUser(user)
                .processorData(null)
                .secretToken(RandomStringUtils.randomAlphanumeric(32))
                .process(Process.REGISTRATION_EMAIL)
                .build();
        processors.getProcessor(Process.REGISTRATION_EMAIL).preData(confirmation, this);
        confirmationRepository.save(confirmation);
        return confirmation;
    }

    @SneakyThrows
    public URI createRelativeUri(Confirmation confirmation) {
        return new URI("/v1/confirmations/confirm/" + confirmation.id + "/" + confirmation.secretToken);
    }

    @SneakyThrows
    public URI createAbsoluteUri(Confirmation confirmation) {
        return new URI(baseUrl+"/v1/confirmations/confirm/" + confirmation.id + "/" + confirmation.secretToken);
    }

    public void userDeleteAllFromProcess(Process process, User user) {
        List<Confirmation> confirmations = user.getConfirmations()
                .stream()
                .filter(p -> p.getProcess() == Process.REGISTRATION_EMAIL)
                .toList();
        confirmationRepository.deleteAll(confirmations);
    }

    public void confirm(String confirmationId, String secretToken) {
        Confirmation confirmation = getConfirmation(confirmationId);

        if(!confirmation.getSecretToken().equals(secretToken)) {
            throw new InvalidSecretTokenException();
        }

        processors.getProcessor(confirmation.getProcess()).process(confirmation, this, ConfirmationResult.ACCEPT);
    }

    public Confirmation getConfirmation(String confirmationId) {
        Optional<Confirmation> confirmation = confirmationRepository.findById(confirmationId);
        if(confirmation.isEmpty()) {
            throw new ConfirmationNotFoundException();
        }
        return confirmation.get();
    }



}
