package com.nhuthao02.social_network.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "API Response")
public class ApiResponse {
    @Schema(description = "API Response code", example = "200")
    String code;
    @Schema(description = "API Response message", example = "Successfully")
    String message;
    @Schema(description = "API Response data")
    Object data;
}
