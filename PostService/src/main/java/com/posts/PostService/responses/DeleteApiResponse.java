package com.posts.PostService.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteApiResponse {

    private  String message;
    private boolean success;

}