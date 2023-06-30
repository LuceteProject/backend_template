package com.lucete.template.info.service;

import com.lucete.template.info.DTO.CommentDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Comment;
import com.lucete.template.info.model.Post;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.CommentRepository;
import com.lucete.template.info.repository.PostRepository;
import com.lucete.template.info.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user_id: " + commentDTO.getUser_id()));
        Post post = postRepository.findById(commentDTO.getPost_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post_id: " + commentDTO.getPost_id()));
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    public CommentDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return convertToDto(comment);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        modelMapper.map(commentDTO, comment);
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        commentRepository.delete(comment);
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Comment 엔티티를 CommentDTO로 변환하는 메소드
    private CommentDTO convertToDto(Comment comment) {
        CommentDTO commentDto = modelMapper.map(comment, CommentDTO.class);
        if (comment.getPost() != null) {
            commentDto.setPost_id(comment.getPost().getId());
            commentDto.setUser_id(comment.getUser().getId());
        }
        return commentDto;
    }

}
