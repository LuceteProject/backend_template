package com.lucete.template.info.service;

import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Board;
import com.lucete.template.info.model.Post;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.BoardRepository;
import com.lucete.template.info.repository.PostRepository;
import com.lucete.template.info.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    public PostService(PostRepository postRepository,ModelMapper modelMapper,UserRepository userRepository,BoardRepository boardRepository){
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
        this.userRepository=userRepository;
        this.boardRepository=boardRepository;
    }
    @Transactional(readOnly = true)
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findWithUserAndBoardById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return convertToDto(post);
    }

    public PostDTO createPost(PostDTO postDto) {
        User user = userRepository.findById(postDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user_id: " + postDto.getUser_id()));
        Board board = boardRepository.findById(postDto.getBoard_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid board_id: " + postDto.getBoard_id()));

        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        post.setBoard(board);
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
        postDto.setCreated(post.getCreated());
        postDto.setUpdated(post.getUpdated());
        return postDto;
    }

}
