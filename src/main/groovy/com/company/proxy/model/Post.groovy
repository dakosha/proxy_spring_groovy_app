package com.company.proxy.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime

@Document(collection = "_posts")
class Post {

    @Id
    String id

    @Indexed
    String userId

    @Indexed
    String userLogin

    LocalDateTime creationDate = LocalDateTime.now()

    String message

    Set<String> likes = []

    List<Comment> comments = []

}
