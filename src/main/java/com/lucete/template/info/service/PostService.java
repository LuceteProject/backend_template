package com.lucete.template.info.service;

import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Post;
import com.lucete.template.info.repository.BoardRepository;
import com.lucete.template.info.repository.PostRepository;
import com.lucete.template.info.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findWithUserAndBoardById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return convertToDto(post);
    }

    public PostDTO createPost(PostDTO postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post = postRepository.save(post);
        return convertToDto(post);
    }

    public PostDTO updatePost(Long id, PostDTO postDto) {
        Post post = postRepository.findWithUserAndBoardById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        modelMapper.map(postDto, post);
        post = postRepository.save(post);
        return convertToDto(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findWithUserAndBoardById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
    @Transactional
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::convertToDto);
    }

    @Transactional
    public List<PostDTO> getPostsByBoardId(Long boardId) {
        List<Post> posts = postRepository.findByBoardId(boardId);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDto = modelMapper.map(post, PostDTO.class);
        postDto.setUser_id(post.getUser().getId());
        postDto.setBoard_id(post.getBoard().getId());
        return postDto;
    }
}
