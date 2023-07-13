package de.merkeg.vsrentbe.auth;

import de.merkeg.vsrentbe.auth.dto.RefreshTokenRequestDTO;
import de.merkeg.vsrentbe.auth.dto.TokenDTO;
import de.merkeg.vsrentbe.exception.RefreshTokenNotValidException;
import de.merkeg.vsrentbe.security.JwtService;
import de.merkeg.vsrentbe.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshToken createLongLivedRefreshToken(User target) {
        RefreshToken token = RefreshToken.builder()
                .refreshToken(createUniqueString())
                .creationTime(LocalDateTime.now())
                .lastRefresh(LocalDateTime.now())
                .owner(target)
                .build();

        refreshTokenRepository.save(token);
        return token;
    }

    public RefreshToken rotateToken(RefreshToken token) {
        token.setRefreshToken(this.createUniqueString());
        token.setLastRefresh(LocalDateTime.now());
        refreshTokenRepository.save(token);
        return token;
    }

    public TokenDTO refreshToken(RefreshTokenRequestDTO data) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(data.getRefreshToken());

        if(optionalRefreshToken.isEmpty()) {
            throw new RefreshTokenNotValidException();
        }
        RefreshToken refreshToken = optionalRefreshToken.get();
        User user = refreshToken.getOwner();

        rotateToken(refreshToken);

        String accessToken = jwtService.createToken(user);

        return TokenDTO.builder()
                .refreshToken(refreshToken.refreshToken)
                .accessToken(accessToken)
                .build();
    }

    private String createUniqueString() {
        return RandomStringUtils.randomAlphabetic(64);
    }
}
