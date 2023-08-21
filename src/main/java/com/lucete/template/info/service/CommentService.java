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
        return convertToDTO(comment);
    }

    public CommentDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return convertToDTO(comment);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        modelMapper.map(commentDTO, comment);
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        List<Comment> childComments= commentRepository.findByParent(id);
        if(childComments.isEmpty()) {
            if(comment.getParent()!=null){
                Comment parentComment = commentRepository.findById(comment.getParent()).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));;
                if(parentComment.getIsDeleted()){
                    commentRepository.delete(parentComment);
                }
            }
            commentRepository.delete(comment);
        }

        else {
            comment.setIsDeleted(true);
            commentRepository.save(comment);
        }
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Comment 엔티티를 CommentDTO로 변환하는 메소드
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setPost_id(comment.getPost().getId());
        commentDTO.setUser_id(comment.getUser().getId());
        commentDTO.setIs_deleted(comment.getIsDeleted());
        return commentDTO;
    }

}
