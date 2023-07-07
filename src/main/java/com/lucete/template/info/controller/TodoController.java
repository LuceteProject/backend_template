package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.TodoDTO;
import com.lucete.template.info.model.Todo;
import com.lucete.template.info.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@Tag(name = "todos", description = "할 일 API")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @Operation(summary = "새로운 할 일 생성", description = "새로운 할 일 정보를 생성합니다.")

    public TodoDTO createTodo(@RequestBody TodoDTO todoDTO) {
        return todoService.createTodo(todoDTO);
    }

    @GetMapping("/{todoId}")
    @Operation(summary = "특정 할 일 정보 조회", description = "할 일 ID를 이용하여 할 일 정보를 조회합니다.")
    public TodoDTO getTodo(@PathVariable Long todoId) {
        return todoService.getTodo(todoId);
    }

    @GetMapping("/userID/{userId}")
    @Operation(summary = "특정 사용자의 모든 할 일 조회", description = "사용자 ID를 이용하여 해당 사용자의 모든 할 일 조회합니다.")
    public ResponseEntity<List<TodoDTO>> getTodosByUserId(@Parameter(description = "할 일을 조회할 사용자의 ID") @PathVariable Long userId) {
        List<TodoDTO> todos = todoService.getTodosByUserId(userId);
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "할 일 정보 수정", description = "할 일 ID를 이용하여 기존 할 일 정보를 수정합니다.")

    public TodoDTO updateTodo(@PathVariable Long todoId, @RequestBody TodoDTO todoDTO) {
        return todoService.updateTodo(todoId, todoDTO);
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "할 일 삭제", description = "할 일 ID를 이용하여 할 일 정보를 삭제합니다.")
    public void deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
    }

    @GetMapping
    @Operation(summary = "모든 할 일 조회")
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }
}
