package com.lucete.template.info.service;

import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Post;
import com.lucete.template.info.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDTO.class);
    }

    @Transactional
    public PostDTO createPost(PostDTO postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        modelMapper.map(postDto, post);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}