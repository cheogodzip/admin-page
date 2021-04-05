package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.service.OrderGroupApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiController implements CrudInterface<OrderGroupApiRequest, OrderGroupApiResponse> {

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Override
    @PostMapping("")  // /api/orderGroup
    public Header<OrderGroupApiResponse> create(@RequestBody Header<OrderGroupApiRequest> request) {
        log.info("{}", request);
        return orderGroupApiLogicService.create(request);
    }

//    @Override
//    @GetMapping("{id}") // /api/orderGroup/{id} - 사용자가 id를 이미 알고 있어야 한다.
//    public Header<OrderGroupApiResponse> read(@PathVariable(name = "id") Long id) {
////        log.info("read id : {}", id);
////        return orderGroupApiLogicService.read(id);
//        return null;
//    }

    @Override
    @GetMapping("{id}")
    public Header<OrderGroupApiResponse> read(Long id) {
        return null;
    }

    @Override
    @PutMapping("") // /api/orderGroup
    public Header<OrderGroupApiResponse> update(@RequestBody Header<OrderGroupApiRequest> request) {
//        log.info("{}");
//        return orderGroupApiLogicService.update(request);
        return null;
    }

    @Override
    @DeleteMapping("{id}") // /api/orderGroup/{id}
    public Header delete(@PathVariable Long id) {
//        log.info("delete : {}", id);
//        return orderGroupApiLogicService.delete(id);
        return null;
    }

}
