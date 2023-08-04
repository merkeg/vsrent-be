package de.merkeg.vsrentbe;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "VS-Rent Backend",
                description = "The backend for the Rent.VS website",
                contact = @Contact(
                        name = "Egor Merk",
                        email = "hello@merkeg.de",
                        url = "https://merkeg.de"
                ),
                version = "1.0"
        )
)
public class OpenAPIConfiguration {
}
