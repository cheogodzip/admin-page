package com.example.study.temp;

import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user2")
public class TempUserApiController implements TempCreateInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private TempUserApiLogicService tempUserApiLogicService;

    @Override
    @PostMapping("") // /api/user2
    public ResponseEntity<Header<UserApiResponse>> create(@RequestBody Header<UserApiRequest> request) {
        return tempUserApiLogicService.create(request);
    }
}
