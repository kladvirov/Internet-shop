package by.kladvirov.security.auth;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.security.JwtService;
import by.kladvirov.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationManager manager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserCreationDto user = userService.findByLogin(request.getLogin());
        String jwtToken = jwtService.generateToken(userMapper.toEntity(user));
        String refreshToken = jwtService.generateRefreshToken(userMapper.toEntity(user));
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse register(UserCreationDto request) {
        UserCreationDto user = UserCreationDto.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthDate(request.getBirthDate())
                .roleIds(request.getRoleIds())
                .build();
        userService.save(user);
        String jwtToken = jwtService.generateToken(userMapper.toEntity(user));
        String refreshToken = jwtService.generateRefreshToken(userMapper.toEntity(user));
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userLogin;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userLogin = jwtService.extractUserLogin(refreshToken);

        if (userLogin != null) {
            UserCreationDto user = this.userService.findByLogin(userLogin);
            UserDetails userDetails = userMapper.toEntity(user);

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
               String accessToken = jwtService.generateToken(userDetails);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .refreshToken(refreshToken)
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
