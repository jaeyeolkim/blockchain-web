package com.okbank.blockchain.common.payload;

import com.okbank.blockchain.common.constants.ResponseCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
public class ErrorResponse extends CommonResponse {

    private final List<FieldError> validation;

    public ErrorResponse(ResponseCode responseCode, List<FieldError> validation) {
        super(responseCode);
        this.validation = validation;
    }

    public static ErrorResponse of(ResponseCode responseCode, BindingResult bindingResult) {
        return new ErrorResponse(responseCode, FieldError.of(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        /**
         * BindingResult to FieldError
         */
        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

}
