package com.example.todo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@ControllerAdvice
// RestController에서 발생되는 예외를 전역적으로 처리할 수 있게 하는 아노테이션.
// 예외 상황에 따른 응답을 REST 방식으로 클라이언트에게 전달할 수 있다.
@RestControllerAdvice
public class GlobalExceptionHandler { // 모든 컨드롤러들의 전역적인 예외 설정

    @ExceptionHandler({RuntimeException.class, NoRegisteredArgumentException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleRuntimeException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class) // 예상하지 못한 에러
    public ResponseEntity<?> handleRuntimeException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
