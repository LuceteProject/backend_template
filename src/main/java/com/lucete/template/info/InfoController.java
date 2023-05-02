package com.lucete.template.info;

import com.lucete.template.info.model.Project;
import com.lucete.template.info.model.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    // 기본적인 json response
    @GetMapping("/info")
    public Object ProjectInfo() {
        Project project = infoService.getProjectInfo();
        return project;
    }

    @GetMapping("/userList")
    public List<User> userList() {
        List<User> userList = infoService.getUserList();

        return userList;
    }
}
