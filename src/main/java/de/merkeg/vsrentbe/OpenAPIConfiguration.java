package de.merkeg.vsrentbe;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Rent.VS Backend",
                description = "The backend for the Rent.VS website",
                contact = @Contact(
                        name = "Egor Merk",
                        email = "hello@merkeg.de",
                        url = "https://merkeg.de"
                )
        )
)
public class OpenAPIConfiguration {
}
