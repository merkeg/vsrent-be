package de.merkeg.vsrentbe.confirmation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/confirmations")
@Tag(name = "Confirmations")
public class ConfirmationResource {

    @PostMapping("/confirm/{confirmationId}/{secretToken}")
    @Operation(summary = "Confirm something")
    @PreAuthorize("isAuthenticated()")
    public void confirm(@PathVariable String confirmationId, @PathVariable String secretToken){

    }

    @PostMapping("/decline/{confirmationId}/{secretToken}")
    @Operation(summary = "Decline something")
    @PreAuthorize("isAuthenticated()")
    public void decline(@PathVariable String confirmationId, @PathVariable String secretToken){

    }
}
