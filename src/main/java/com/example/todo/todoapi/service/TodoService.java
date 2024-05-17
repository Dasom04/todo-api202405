package com.example.todo.todoapi.service;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.response.TodoDetailResponseDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.entity.Todo;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;


    public TodoListResponseDTO create(TodoCreateRequestDTO requestDTO) throws Exception {
        todoRepository.save(requestDTO.toEntity());
        log.info("할 일 저장 완료! 제목: {}", requestDTO.getTitle());
        return retrieve();
    }

    // 할 일 목록 가져오기
    public TodoListResponseDTO retrieve() throws Exception {
        List<Todo> entityList = todoRepository.findAll();

        List<TodoDetailResponseDTO> dtoList = entityList.stream()
//                .map(entity -> new TodoDetailResponseDTO(entity))
                .map(TodoDetailResponseDTO::new) // 람다식
                .collect(Collectors.toList());

        return TodoListResponseDTO.builder()
                .todos(dtoList)
                .build();

    }

    // 할 일 목록 삭제하기
    public TodoListResponseDTO delete(final String todoId) throws Exception { // 매개변수에 final을 붙이면 service에서 controller로 넘어온 데이터(매개변수)를 변경할 수 없다.

            Todo todo = todoRepository.findById(todoId).orElseThrow(
                    () -> {
                        log.error("ID가 존재하지 않아서 실패했습니다. - ID: {}", todoId);
                        throw new RuntimeException("id가 존재하지 않아서 삭제에 실패했습니다.");
                    }
            );
            todoRepository.deleteById(todoId);
            return retrieve();

    }

}