package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailApiController extends CrudController<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

}
