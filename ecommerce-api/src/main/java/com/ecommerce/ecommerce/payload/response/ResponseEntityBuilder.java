package com.ecommerce.ecommerce.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntityBuilder {
    private HttpStatus status;
    private Object data;
    private String message;

    public ResponseEntityBuilder setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseEntityBuilder setData(Object data) {
        this.data = data;
        return this;
    }

    public ResponseEntityBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntity<ApiResponse> build() {
        ApiResponse response = ApiResponse.builder()
                .status(status.getReasonPhrase())
                .data(data)
                .message(message)
                .build();

        return new ResponseEntity<>(response, status);
    }
}
