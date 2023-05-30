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
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    public CommentDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return modelMapper.map(comment, CommentDTO.class);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        modelMapper.map(commentDTO, comment);
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDTO.class);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        commentRepository.delete(comment);
    }
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        return commentDTOs;
    }

}