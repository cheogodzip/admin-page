package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import jdk.javadoc.internal.doclets.formats.html.markup.Head;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    // 1. request data 가져오기
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1.
        UserApiRequest userApiRequest = request.getData();

        // 2.
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = userRepository.save(user);

        // 3.
        return response(newUser);

    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    private Header<UserApiResponse> response(User user){
        // user -> userApiResponse 만들어서 리턴ㅁ
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword()) // 암호화하거나 해야 함
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();
        
        // Header  + data 리턴
        return Header.OK(userApiResponse);
    }
}
