package de.merkeg.vsrentbe.user;

import de.merkeg.vsrentbe.auth.RefreshTokenService;
import de.merkeg.vsrentbe.auth.dto.TokenDTO;
import de.merkeg.vsrentbe.auth.dto.UserLoginGuestDTO;
import de.merkeg.vsrentbe.auth.dto.UserRegisterGuestDTO;
import de.merkeg.vsrentbe.confirmation.ConfirmationService;
import de.merkeg.vsrentbe.confirmation.Process;
import de.merkeg.vsrentbe.exception.PasswordDoesNotMatchException;
import de.merkeg.vsrentbe.exception.UserAlreadyExistsException;
import de.merkeg.vsrentbe.exception.UserDoesNotExistException;
import de.merkeg.vsrentbe.quota.UserQuota;
import de.merkeg.vsrentbe.quota.UserQuotaRepository;
import de.merkeg.vsrentbe.security.JwtService;
import de.merkeg.vsrentbe.util.Base58;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserQuotaRepository userQuotaRepository;
    private final ConfirmationService confirmationService;

    public boolean userWithEmailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User registerUser(UserRegisterGuestDTO data) {
        if(userWithEmailAlreadyExists(data.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        User newUser = User.builder()
                .id(Base58.uuid58())
                .fullName(data.getFullName())
                .phoneNumber(data.getPhoneNumber())
                .email(data.getEmail())
                .registrationType(UserRegistrationType.GUEST)
                .password(passwordEncoder.encode(data.getPassword()))
                .role(Role.ROLE_USER)
                .locked(false)
                .emailVerified(false)
                .build();

        UserQuota quota = UserQuota.builder()
                .usedQuota(0)
                .maxQuota(209715200L)
                .build();
        newUser.setQuota(quota);

        userQuotaRepository.save(quota);
        userRepository.save(newUser);

        quota.setUser(newUser);
        userQuotaRepository.save(quota);
        return newUser;
    }

    public TokenDTO loginAsGuest(UserLoginGuestDTO data) {
        Optional<User> optionalUser = userRepository.findByEmail(data.getEmail());
        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExistException();
        }
        User user = optionalUser.get();

        // Check if password matches
        boolean matches = passwordEncoder.matches(data.getPassword(), user.getPassword());
        if(!matches) {
            throw new PasswordDoesNotMatchException();
        }

        String accessToken = jwtService.createToken(user);
        String refreshToken = refreshTokenService.createLongLivedRefreshToken(user).getRefreshToken();

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        confirmationService.userDeleteAllFromProcess(Process.REGISTRATION_EMAIL, user);
        userRepository.save(user);
    }


}
