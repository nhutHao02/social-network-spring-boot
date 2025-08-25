package com.nhuthao02.social_network.controllers;

import com.nhuthao02.social_network.dtos.requests.comment.AddCommentRequest;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.ICommentService;
import com.nhuthao02.social_network.utils.ApiResponse;
import com.nhuthao02.social_network.utils.JwtToken;
import com.nhuthao02.social_network.utils.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(path = "/api/v1/comment")
@Tag(name = "Comment", description = "APis realated to comment")
public class CommentController {
    final
    ICommentService service;

    final
    JwtToken jwtToken;

    public CommentController(ICommentService service, JwtToken jwtToken) {
        this.service = service;
        this.jwtToken = jwtToken;
    }

    @Operation(
            summary  = "Add comment to tweet",
            description = "Add new comment to tweet"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/post")
    public ResponseEntity<ApiResponse> post(@RequestBody AddCommentRequest request, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        boolean rs = service.addComment(request);

        return rs ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }

    @Operation(
            summary  = "Update comment on tweet",
            description = "Update comment on tweet"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/update-comment/{commentId}")
    public ResponseEntity<ApiResponse> update(@PathVariable String commentId, @RequestBody AddCommentRequest request, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        boolean rs = service.updateComment(commentId, request);

        return rs ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }

    @Operation(
            summary  = "Delete comment on tweet",
            description = "Delete comment on tweet"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/delete/{commentId}")
    public ResponseEntity<ApiResponse> update(@PathVariable String commentId, HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        boolean rs = service.deleteComment(commentId);

        return rs ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }

    @Operation(
            summary  = "Get comments by tweetId",
            description = "Get comments by tweetId"
    )
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/get-comments/{tweetId}")
    public ResponseEntity<ApiResponse> getComments(@PathVariable String tweetId,
                                                   @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                                                   HttpServletRequest servletRequest) {
        String token = jwtToken.getBearToken(servletRequest);

        if (!jwtToken.verifyToken(token)) throw new AppException(ErrorCode.INVALID_TOKEN);

        var rs = service.getComments(tweetId, page, limit);

        return !rs.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(rs)
                        .build())
                :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                        .code(ResponseCode.FAILURE.getCode())
                        .message(ResponseCode.FAILURE.getMessage())
                        .build())
                ;
    }

}
