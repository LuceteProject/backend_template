package com.lucete.template.info.controller;

import com.lucete.template.info.model.Board;
import com.lucete.template.info.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @PostMapping
    public ResponseEntity<Board> addBoard(@RequestBody Board newBoard) {
        Board savedBoard = boardService.addBoard(newBoard);
        return ResponseEntity.ok(savedBoard);
    }

    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board updatedBoard) {
        updatedBoard.setId(id);
        return boardService.updateBoard(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}
