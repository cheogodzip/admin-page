package com.example.study.controller.api;

import com.example.study.model.network.Header;
import com.example.study.model.network.response.MainPageInfoApiResponse;
import com.example.study.service.MainPageInfoApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mainPage")
public class MainPageInfoApiController {

    @Autowired
    private MainPageInfoApiLogicService mainPageInfoApiLogicService;

    @GetMapping
    public Header<MainPageInfoApiResponse> read(){
        return mainPageInfoApiLogicService.read();
    }
}
