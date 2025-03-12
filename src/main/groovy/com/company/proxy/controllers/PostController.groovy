package com.company.proxy.controllers

import com.company.proxy.model.Comment
import com.company.proxy.model.Post
import com.company.proxy.service.PostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService
    }

    @PostMapping
    Post createPost(@RequestBody Post post) {
        postService.createPost(post)
    }

    @PutMapping
    Post updatePost(@RequestBody Post post) {
        postService.updatePost(post)
    }

    @GetMapping
    List<Post> getPosts() {
        postService.getAllPosts()
    }

    @GetMapping("/{postId}")
    Post getPostById(@PathVariable("postId") String postId) {
        postService.getPostById(postId)
    }

    @GetMapping("/{postId}/comments")
    List<Comment> getAllCommentsOnPost(@PathVariable("postId") String postId) {
        postService.getPostById(postId).comments
    }

    @PostMapping("/{postId}/comments")
    void addCommentToPost(@PathVariable("postId") String postId, @RequestBody Comment comment) {
        postService.addCommentToPost(postId, comment)
    }

    @PutMapping("/{postId}/like/{userId}")
    void addLikeToPost(@PathVariable("postId") String postId, @PathVariable("userId") String userId) {
        postService.addLikeToPost(postId, userId, true)
    }

    @PutMapping("/{postId}/dislike/{userId}")
    void addDislikeToPost(@PathVariable("postId") String postId, @PathVariable("userId") String userId) {
        postService.addLikeToPost(postId, userId, false)
    }

}
