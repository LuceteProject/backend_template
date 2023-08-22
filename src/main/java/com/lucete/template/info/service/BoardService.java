package com.lucete.template.info.service;

import com.lucete.template.info.DTO.*;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Board;
import com.lucete.template.info.model.Todo;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.BoardRepository;
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
    public List<BoardDTO> getAllBoards(){
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private BoardDTO convertToDTO(Board board) {
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        boardDTO.setPermissionCode(board.getPermissionCode());
        return boardDTO;
    }
}