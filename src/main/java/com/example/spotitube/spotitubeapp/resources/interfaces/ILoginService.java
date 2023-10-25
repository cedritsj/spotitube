package com.example.spotitube.spotitubeapp.resources.interfaces;

import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;

public interface ILoginService {
    UserDTO login(LoginRequestDTO loginRequestDTO);

    UserDTO verifyToken(String token);

    int getUserID(String token);

    UserDTO getUserWithToken(String token);
}
