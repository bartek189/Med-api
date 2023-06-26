package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.jwt.JwtTokenUtil;
import pl.kurs.jwt.JwtUserDetailsService;
import pl.kurs.model.dto.JwtRequestDto;
import pl.kurs.model.dto.JwtResponseDto;
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class JwtController {
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDto> createAuthenticationToken(@RequestBody JwtRequestDto requestDto) throws Exception {
        authenticate(requestDto.getUserName(), requestDto.getPassword());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(requestDto.getUserName());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    private void authenticate(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException | BadCredentialsException exc) {
            throw new Exception(exc.getMessage());
        }
    }
}
