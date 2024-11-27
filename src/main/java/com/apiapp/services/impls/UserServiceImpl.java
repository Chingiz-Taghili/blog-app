package com.apiapp.services.impls;

import com.apiapp.dtos.user.RegisterDto;
import com.apiapp.models.User;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.repositories.UserRepository;
import com.apiapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse register(RegisterDto registerDto) {
        User findUser = userRepository.findByEmail(registerDto.getEmail());
        if (findUser != null) {
            return new ApiResponse("User already exist", false);
        }
        User user = modelMapper.map(registerDto, User.class);
        String password = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        user.setConfirmationToken("a624bd1b2sfd14c");
        userRepository.save(user);
        return new ApiResponse("Confirm the email", true);
    }
}
