package com.jvmless.shop.usermanagement;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${usermanagement.url}", name = "user-client", fallback = UserServiceIntegrationApiImpl.class)
public interface UserServiceIntegrationApi extends UserRepository{

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    User find(@RequestParam("id") UserId id);

}
