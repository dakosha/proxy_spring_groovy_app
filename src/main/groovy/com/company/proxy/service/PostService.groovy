package com.company.proxy.service

import com.company.proxy.exceptions.MessageEmpty
import com.company.proxy.model.Comment
import com.company.proxy.model.Post
import com.company.proxy.repository.PostRepository
import com.company.proxy.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService)
    private final PostRepository postRepository
    private final UserRepository userRepository

    PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository
        this.userRepository = userRepository
    }

    Post createPost(Post post) {
        validatePost(post)

        postRepository.save(post)
    }

    List<Post> getAllPosts() {
        postRepository.findAll()
    }

    List<Post> getAllPostsByUserId(String userId) {
        postRepository.findByUserId(userId).orElse([])
    }

    Post getPostById(String postId) {
        postRepository.findById(postId).orElseThrow()
    }

    private void validatePost(Post post) {
        if (post.userId?.trim()) {
            logger.info("Using userId for Post creation")
            post.userLogin = userRepository.findById(post.userId)
                    .orElseThrow().login
        } else if (post.userLogin?.trim()) {
            logger.info("Using userLogin for Post creation")
            post.userId = userRepository.findByLogin(post.userLogin)
                    .orElseThrow().id
        }
    }

    Post addCommentToPost(String postId, Comment comment) {
        validateComment(comment)
        def post = postRepository.findById(postId).orElseThrow()

        post.comments << comment

        postRepository.save(post)
    }

    void validateComment(Comment comment) {
        def user = userRepository.findByLogin(comment.userLogin)
                .orElseGet { userRepository.findById(comment.userId).orElseThrow() }

        comment.userLogin = user?.login
        comment.userId = user?.id

        assert comment.message?.trim()
    }

    void addLikeToPost(String postId, String userId, Boolean like) {
        def post = postRepository.findById(postId).orElseThrow()
        def user = userRepository.findById(userId).orElseThrow()?.id

        like ? post.likes.add(userId) : post.likes.remove(userId)

        postRepository.save(post)
    }

    def updatePost(Post post) {
        def postFromDatabase = postRepository.findById(post.id).orElseThrow()

        if (post.message?.trim()) {
            postFromDatabase.message = post.message
            return postRepository.save(postFromDatabase)
        }

        throw new MessageEmpty()
    }

    List<Post> getUserWall(String id) {
        def user = userRepository.findById(id).orElseThrow()
        def subscriptionUserIds = (user?.subscriptions ?: []) + [id]
        def posts = []
        if (subscriptionUserIds) {
            posts = postRepository.findByUserIdIn(subscriptionUserIds).orElseThrow()
            posts.sort { it -> -it.creationDate.nano }
        }
        posts
    }

}
