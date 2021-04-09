package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;

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

        Item newItem = baseRepository.save(item);

        // 3. 생성된 데이터 -> ItemApiResponse return
        return Header.OK(response(newItem));

    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        // id -> repository getOne, getById
        // item -> itemApiResponse return
        return baseRepository.findById(id)
            .map(this::response) // item이 있다면
            .map(itemApiResponse -> Header.OK(itemApiResponse))
            .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        // data 가져오기
        ItemApiRequest body = request.getData();

        // id로 데이터를 찾고
        Optional<Item> optional = baseRepository.findById(body.getId());

        return optional.map(entityItem -> {
            // 업데이트
            entityItem.setStatus(body.getStatus())
                    .setName(body.getName())
                    .setTitle(body.getTitle())
                    .setContent(body.getContent())
                    .setPrice(body.getPrice())
                    .setBrandName(body.getBrandName())
                    .setRegisteredAt(body.getRegisteredAt())
                    .setUnregisteredAt(body.getUnregisteredAt())
                    ;
            return entityItem;
        })
        .map(newEntityItem -> baseRepository.save(newEntityItem)) // update. 새로운 item 리턴
        .map(item -> response(item)) // 응답 api 메시지 만들기
        .map(itemApiResponse -> Header.OK(itemApiResponse))
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        // id로 repository에서 item 찾고
        // delete, 메시지 반환
        return baseRepository.findById(id)
            .map(item -> {
                baseRepository.delete(item);
                return Header.OK();
        })
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public ItemApiResponse response(Item item){
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

        return body;
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = baseRepository.findAll(pageable);
        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElemets(items.getNumberOfElements())
                .build();

        return Header.OK(itemApiResponseList, pagination);
    }
}
