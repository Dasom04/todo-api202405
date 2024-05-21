package com.example.todo.userapi.dto.response;

import com.example.todo.userapi.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// 로그인 성공 후 클라이언트에게 전송할 데이터 객체
@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private String email;

    private String userName;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime joinDate;

    private String token; // 인증 코큰 (핵심)

    public LoginResponseDTO(User user, String token) {
        this.email  = user.getEmail();
        this.userName = user.getUserName();
        this.joinDate = LocalDateTime.from(user.getJoinDate()); // LocalDateTime 타입이 다르면 from을 이용해서 넣어라!
        this.token = token;
    }
}
