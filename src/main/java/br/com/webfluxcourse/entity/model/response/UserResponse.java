package br.com.webfluxcourse.entity.model.response;

public record UserResponse(
        String id,
        String name,
        String email,
        String password) {
}
