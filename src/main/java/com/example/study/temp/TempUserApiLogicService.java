package com.example.study.temp;

import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TempUserApiLogicService implements TempCreateInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Header<UserApiResponse>> create(Header<UserApiRequest> request) {

        // request data 가져와서 email 검사
        UserApiRequest userApiRequest = request.getData();
        Optional<User> optional = userRepository.findByEmail(userApiRequest.getEmail());

        // 존재하면 에러 메시지, http status code 409 반환
        // 존재하지 않는다면 User 생성, 저장

        if(optional.isPresent()){
            return new ResponseEntity<>(Header.ERROR("중복된 이메일이 존재합니다."), HttpStatus.CONFLICT);
        }

        // UserApiResponse 리턴
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISTERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser =  userRepository.save(user);
        return new ResponseEntity<Header<UserApiResponse>>(response(newUser), HttpStatus.OK);
    }

    private Header<UserApiResponse> response(User user){
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        return Header.OK(userApiResponse);
    }
}
