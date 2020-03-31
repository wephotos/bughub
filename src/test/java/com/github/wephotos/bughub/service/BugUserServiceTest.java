package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.BugUser;
import com.github.wephotos.bughub.entity.HubUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BugUserServiceTest {
    @Resource
    private BugUserService bugUserService;

    @Test
    void handOver() {
        BugUser bugUser = new BugUser();
        bugUser.setBugId("cc0eb5ccda3347c7b6a474dd4a22f8c0");
        HubUser hubUser = new HubUser();
        hubUser.setId("1");
        bugUserService.handOver(bugUser,hubUser);
    }
}