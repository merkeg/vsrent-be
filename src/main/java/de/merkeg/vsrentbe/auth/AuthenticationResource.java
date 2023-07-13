package de.merkeg.vsrentbe.auth;

import de.merkeg.vsrentbe.auth.dto.RefreshTokenRequestDTO;
import de.merkeg.vsrentbe.auth.dto.TokenDTO;
import de.merkeg.vsrentbe.auth.dto.UserLoginGuestDTO;
import de.merkeg.vsrentbe.auth.dto.UserRegisterGuestDTO;
import de.merkeg.vsrentbe.membership.dto.OrgMembershipMapper;
import de.merkeg.vsrentbe.user.User;
import de.merkeg.vsrentbe.user.UserService;
import de.merkeg.vsrentbe.user.dto.UserDTO;
import de.merkeg.vsrentbe.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationResource {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login/guest")
    @Operation(summary = "Login with guest credentials")
    public TokenDTO loginGuest(@Valid @RequestBody UserLoginGuestDTO body) {
        return userService.loginAsGuest(body);
    }

    @PostMapping("/register")
    @Operation(summary = "Register with credentials")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterGuestDTO body) throws URISyntaxException {
        User newUser = userService.registerUser(body);
        return ResponseEntity.created(new URI("/v1/users/"+newUser.getId())).body("Registered user with new id: "+ newUser.getId());
    }

    @PostMapping("/refresh")
    @Operation(summary = "Get a new access token with a refresh token")
    public TokenDTO refresh(@Valid @RequestBody RefreshTokenRequestDTO requestDTO) {
        return refreshTokenService.refreshToken(requestDTO);
    }

    @GetMapping("/self")
    @Operation(summary = "Get information about yourself")
    @PreAuthorize("hasAuthority('self:auth:info')")
    public ResponseEntity<UserDTO> self(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info(user);
        UserDTO dto = UserMapper.INSTANCE.userToInfoDTO(user);
        return ResponseEntity.ok(dto);
    }



}
