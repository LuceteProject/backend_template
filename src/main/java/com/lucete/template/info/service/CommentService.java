package com.lucete.template.info.service;

import com.lucete.template.info.DTO.CommentDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Comment;
import com.lucete.template.info.repository.CommentRepository;
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

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
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
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return commentDTOs;
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
