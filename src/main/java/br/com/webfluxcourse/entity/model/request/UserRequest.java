package br.com.webfluxcourse.entity.model.request;

public record UserRequest(
        String name,
        String email,
        String password
) {
}

