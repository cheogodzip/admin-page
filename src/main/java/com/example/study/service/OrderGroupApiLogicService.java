package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderGroupApiLogicService implements CrudInterface<OrderGroupApiRequest, OrderGroupApiResponse> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. request data 가져오기
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest body = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(body.getStatus())
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .arrivalDate(body.getArrivalDate())
                .user(userRepository.getOne(body.getUserId()))
                .build();


        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);

        return response(newOrderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
//        // id -> repository getOne, getById
//        // user -> userApiResponse return
//        return userRepository.findById(id)
//            .map(user -> response(user)) // user가 있다면
//            .orElseGet(() -> Header.ERROR("데이터 없음")
//            );
        return null;
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
//        // data를 가져오고
//        UserApiRequest userApiRequest = request.getData();
//
//        // id로 데이터를 찾고
//        Optional<User> optional = userRepository.findById(userApiRequest.getId());
//
//        return optional.map(user -> {
//            // 업데이트
//            user.setAccount(userApiRequest.getAccount())
//                    .setPassword(userApiRequest.getPassword())
//                    .setStatus(userApiRequest.getStatus())
//                    .setPhoneNumber(userApiRequest.getPhoneNumber())
//                    .setEmail(userApiRequest.getEmail())
//                    .setRegisteredAt(userApiRequest.getRegisteredAt())
//                    .setUnregisteredAt(userApiRequest.getUnregisteredAt())
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
//        Optional<User> optional = userRepository.findById(id);
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

    private Header<OrderGroupApiResponse> response(OrderGroup orderGroup){

        OrderGroupApiResponse body = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();

        return Header.OK(body);
    }
}
