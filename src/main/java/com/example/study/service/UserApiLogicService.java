package com.example.study.service;

import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data 가져오기
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = baseRepository.save(user);

        // 3. UserApiResponse 리턴
        return response(newUser);

    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        // id -> repository getOne, getById
        // user -> userApiResponse return
        return baseRepository.findById(id)
            .map(user -> response(user)) // user가 있다면
            .orElseGet(() -> Header.ERROR("데이터 없음")
            );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // data를 가져오고
        UserApiRequest userApiRequest = request.getData();

        // id로 데이터를 찾고
        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        return optional.map(user -> {
            // 업데이트
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt())
                    ;
            return user;
        })
        .map(user -> baseRepository.save(user)) // update. 새로운 user 리턴
        .map(updateUser -> response(updateUser)) // 응답 api 메시지 만들기
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        // id로 repository에서 user를 찾고
        Optional<User> optional = baseRepository.findById(id);

        // delete, 메시지 반환
        return optional.map(user -> {
            baseRepository.delete(user);

            return Header.OK();
        })
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<UserApiResponse> response(User user){
        // user -> userApiResponse 만들어서 리턴
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
