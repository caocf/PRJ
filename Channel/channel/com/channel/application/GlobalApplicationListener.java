package com.channel.application;

import com.channel.permission.PermissionService;
import javafx.application.Application;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 25019 on 2015/12/9.
 */
@Service
public class GlobalApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource(name = "permissionService")
    private PermissionService permissionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            this.permissionService.initUserRefect();
        }
    }
}
