package de.merkeg.vsrentbe.confirmation;

import de.merkeg.vsrentbe.user.User;
import de.merkeg.vsrentbe.util.Base58;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
                .build();
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



}
