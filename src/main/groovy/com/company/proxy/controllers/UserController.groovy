package com.company.proxy.controllers

import com.company.proxy.model.Post
import com.company.proxy.model.User
import com.company.proxy.service.PostService
import com.company.proxy.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserService userService
    private final PostService postService

    UserController(UserService userService, PostService postService) {
        this.userService = userService
        this.postService = postService
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    Optional<User> getUser(@PathVariable String id) {
        return userService.getUserById(id)
    }

    @PostMapping
    User createUser(@RequestBody User user) {
        return userService.createUser(user)
    }

    @PutMapping
    User modifyUser(@RequestBody User user) {
        return userService.updateUser(user)
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable String id) {
        userService.deleteUser(id)
    }

    @PostMapping("/{id}/follow/{targetUserId}")
    void followUser(@PathVariable("id") String id, @PathVariable("targetUserId") String targetUserId) {
        userService.followUser(id, targetUserId, true)
    }

    @PostMapping("/{id}/unfollow/{targetUserId}")
    void unfollowUser(@PathVariable("id") String id, @PathVariable("targetUserId") String targetUserId) {
        userService.followUser(id, targetUserId, false)
    }

    @GetMapping("/{id}/posts")
    List<Post> getPostsByUser(@PathVariable("id") String id) {
        postService.getAllPostsByUserId(id)
    }

    @GetMapping("/{id}/wall")
    List<Post> getUserWall(@PathVariable("id") String id) {
        postService.getUserWall(id)
    }

}
