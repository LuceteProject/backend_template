package com.lucete.template.info.service;

import com.lucete.template.info.DTO.BoardDTO;
import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Board;
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
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        return modelMapper.map(board, BoardDTO.class);
    }

    @Transactional
    public BoardDTO createBoard(BoardDTO boardDto) {
        Board board = modelMapper.map(boardDto, Board.class);
        board = boardRepository.save(board);
        return modelMapper.map(board, BoardDTO.class);
    }

    @Transactional
    public BoardDTO updateBoard(Long id, BoardDTO boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        modelMapper.map(boardDto, board);
        board = boardRepository.save(board);
        return modelMapper.map(board, BoardDTO.class);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        boardRepository.delete(board);
    }
    public Page<BoardDTO> getAllPosts(Pageable pageable) {
        return boardRepository.findAll(pageable).map(board -> modelMapper.map(board, BoardDTO.class));
    }
}