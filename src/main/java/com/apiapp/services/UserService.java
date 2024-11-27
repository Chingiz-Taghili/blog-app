package com.apiapp.services;

import com.apiapp.dtos.user.RegisterDto;
import com.apiapp.payloads.ApiResponse;

public interface UserService {
    ApiResponse register(RegisterDto registerDto);
}
