package com.lucete.template.info.service;

import com.lucete.template.info.DTO.TodoDTO;
import com.lucete.template.info.config.ResourceNotFoundException;
import com.lucete.template.info.model.Todo;
import com.lucete.template.info.model.User;
import com.lucete.template.info.repository.TodoRepository;
import com.lucete.template.info.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper, UserRepository userRepository){
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public TodoDTO createTodo(TodoDTO todoDTO) {
        User user = userRepository.findById(todoDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user_id: " + todoDTO.getUserId()));
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        todo.setUser(user);
        todo = todoRepository.save(todo);
        return convertToDTO(todo);
    }

    public TodoDTO getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));
        return convertToDTO(todo);
    }

    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));
        modelMapper.map(todoDTO, todo);
        Todo updatedTodo = todoRepository.save(todo);
        return convertToDTO(updatedTodo);
    }

    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));
        todoRepository.delete(todo);
    }

    public List<TodoDTO> getAllTodos(){
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> getTodosByUserId(Long userId) {
        List<Todo> todos = todoRepository.findByUserId(userId);
        return todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO todoDTO = modelMapper.map(todo, TodoDTO.class);
        todoDTO.setUserId(todo.getUser().getId());
        todoDTO.setTeamCode(todo.getTeamCode());
        return todoDTO;
    }
}
