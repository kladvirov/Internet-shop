package by.kladvirov.security.auth;

import by.kladvirov.dto.UserCreationDto;
import by.kladvirov.mapper.UserMapper;
import by.kladvirov.security.JwtService;
import by.kladvirov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return AuthenticationResponse.builder()
                .token(jwtToken)
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
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
