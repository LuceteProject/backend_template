package com.lucete.template.info.service;

import com.lucete.template.info.DTO.BoardDTO;
import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.DTO.UserDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Board;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    public BoardService(BoardRepository boardRepository, ModelMapper modelMapper){
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        return convertToDTO(board);
    }

    @Transactional
    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        board = boardRepository.save(board);
        return convertToDTO(board);
    }

    @Transactional
    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        modelMapper.map(boardDTO, board);
        board = boardRepository.save(board);
        return convertToDTO(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        boardRepository.delete(board);
    }
    public Page<BoardDTO> getAllPosts(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(this::convertToDTO);
    }
    private BoardDTO convertToDTO(Board board) {
        return modelMapper.map(board, BoardDTO.class);
    }
}