package com.lucete.template.info;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucete.template.info.model.Project;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.UserRepository;

@Service
public class InfoService {

    private final UserRepository userRepository;

    public InfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Project getProjectInfo() {

        Project project = new Project();
        project.projectName = "lucete";
        project.author = "array";
        project.createdDate = new Date();

        return project;
    }

    public List<User> getUserList() {
        return this.userRepository.findList();
    }
}