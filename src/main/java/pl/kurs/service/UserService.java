package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.model.dto.UserDto;
import pl.kurs.model.entity.User;
import pl.kurs.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public User saveNewUser(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = modelMapper.map(userDto, User.class);

        return repository.save(user);
    }
}
