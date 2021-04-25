package com.example.study.service;

import com.example.study.model.network.Header;
import com.example.study.model.network.response.MainPageInfoApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.PartnerRepository;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainPageInfoApiLogicService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    public Header<MainPageInfoApiResponse> read() {

        Long userCount = userRepository.count();
        Long itemCount = itemRepository.count();
        Long orderGroupCount = orderGroupRepository.count();
        Long partnerCount = partnerRepository.count();

        MainPageInfoApiResponse mainPageInfoApiResponse = MainPageInfoApiResponse.builder()
                .userCount(userCount)
                .itemCount(itemCount)
                .orderGroupCount(orderGroupCount)
                .partnerCount(partnerCount)
                .build();
        log.info("mainPageInfoApiResponse : {}", mainPageInfoApiResponse);
        return Header.OK(mainPageInfoApiResponse);
    }
}
