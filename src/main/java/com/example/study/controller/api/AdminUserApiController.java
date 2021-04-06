package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.AdminUser;
import com.example.study.model.network.request.AdminUserApiRequest;
import com.example.study.model.network.response.AdminUserApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adminUser")
public class AdminUserApiController extends CrudController<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

}
