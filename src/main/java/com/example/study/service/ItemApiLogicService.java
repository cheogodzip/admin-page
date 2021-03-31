package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemApiLogicService implements CrudInterface<ItemApiRequest, ItemApiResponse> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        // 1. request data 가져오기
        ItemApiRequest body = request.getData();

        // 2. Item 생성
        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = itemRepository.save(item);

        // 3. 생성된 데이터 -> ItemApiResponse return
        return response(newItem);

    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
//        // id -> repository getOne, getById
//        // user -> userApiResponse return
//        return itemRepository.findById(id)
//            .map(user -> response(user)) // user가 있다면
//            .orElseGet(() -> Header.ERROR("데이터 없음")
//            );
        return null;
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
//        // data를 가져오고
//        ItemApiRequest itemApiRequest = request.getData();
//
//        // id로 데이터를 찾고
//        Optional<User> optional = userRepository.findById(itemApiRequest.getId());
//
//        return optional.map(user -> {
//            // 업데이트
//            item.(itemApiRequest.getAccount())
//                    .setPassword(itemApiRequest.getPassword())
//                    .setStatus(itemApiRequest.getStatus())
//                    .setPhoneNumber(itemApiRequest.getPhoneNumber())
//                    .setEmail(itemApiRequest.getEmail())
//                    .setRegisteredAt(itemApiRequest.getRegisteredAt())
//                    .setUnregisteredAt(itemApiRequest.getUnregisteredAt())
//                    ;
//            return user;
//        })
//        .map(user -> userRepository.save(user)) // update. 새로운 user 리턴
//        .map(updateUser -> response(updateUser)) // 응답 api 메시지 만들기
//        .orElseGet(() -> Header.ERROR("데이터 없음"));
        return null;
    }

    @Override
    public Header delete(Long id) {
//        // id로 repository에서 user를 찾고
//        Optional<Item> optional = itemRepository.findById(id);
//
//        // delete, 메시지 반환
//        return optional.map(user -> {
//            userRepository.delete(user);
//
//            return Header.OK();
//        })
//        .orElseGet(() -> Header.ERROR("데이터 없음"));
        return null;
    }

    private Header<ItemApiResponse> response(Item item){
        // item -> itemApiResponse 만들어서 리턴
        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();
        
        // Header  + data 리턴
        return Header.OK(body);
    }
}
