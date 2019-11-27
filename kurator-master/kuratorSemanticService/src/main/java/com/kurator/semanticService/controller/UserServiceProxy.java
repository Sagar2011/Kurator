package com.kurator.semanticService.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "kuratorUserManagementService")//Service Id of kuratorUserManagementService
public interface UserServiceProxy {

    @GetMapping("/user/{email}")
    void markUserStatus(@PathVariable String email);

}
