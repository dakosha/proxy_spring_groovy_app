package com.company.proxy.service

import com.company.proxy.model.Post
import com.company.proxy.model.User
import com.company.proxy.repository.PostRepository
import com.company.proxy.repository.UserRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class PostServiceSpec extends Specification {

    def postRepository = Mock(PostRepository)
    def userRepository = Mock(UserRepository)
    @Subject
    def postService = new PostService(postRepository, userRepository)
    @Subject
    def userService = new UserService(userRepository)

    def "should return all posts by user id"() {
        given:
        def user = new User(id: "234", login: "dauren@gmail.com")
        userRepository.findById("234") >> user
        def post = new Post(id: "111",
                userId: "234",
                message: "Post message",
                creationDate: LocalDateTime.now(),
                userLogin: "dauren@gmail.com")
        postRepository.findByUserId("234") >> Optional.of([post])

        when:
        def result = postService.getAllPostsByUserId("234")

        then:
        result == [post]
    }

}
