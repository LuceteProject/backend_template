package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.BoardDTO;
import com.lucete.template.info.DTO.PostDTO;
import com.lucete.template.info.model.Board;
import com.lucete.template.info.service.BoardService;
import com.lucete.template.info.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/boards")
@Tag(name = "boards", description = "게시판 API")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) { this.boardService = boardService; }

    @GetMapping("/{boardId}")
    @Operation(summary = "특정 게시판 정보 조회", description = "게시판 ID를 이용하여 게시판 정보를 조회합니다.")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoardById(boardId));
    }

    @PostMapping
    @Operation(summary = "새로운 게시판 생성", description = "새로운 게시판 정보를 입력하여 생성합니다.")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDto) {
        return ResponseEntity.ok(boardService.createBoard(boardDto));
    }

    @PutMapping("/{boardId}")
    @Operation(summary = "게시판 정보 수정", description = "게시판 ID를 이용하여 기존 게시판 정보를 수정합니다.")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long boardId, @RequestBody BoardDTO boardDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, boardDto));
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "게시판 삭제", description = "게시판 ID를 이용하여 게시판 정보를 삭제합니다.")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @Operation(summary = "모든 게시판 정보 조회")
    public ResponseEntity<List<BoardDTO>> getAllBoards() { return ResponseEntity.ok(boardService.getAllBoards()); }

}
