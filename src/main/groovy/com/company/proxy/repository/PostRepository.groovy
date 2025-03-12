package com.company.proxy.repository

import com.company.proxy.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, String> {

    Optional<List<Post>> findByUserId(String userId)
    Optional<List<Post>> findByUserLogin(String userLogin)

    Optional<List<Post>> findByUserIdIn(Collection<String> userIds)

}
