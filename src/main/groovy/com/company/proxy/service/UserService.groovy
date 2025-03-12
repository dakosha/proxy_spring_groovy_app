package com.company.proxy.service

import com.company.proxy.model.User
import com.company.proxy.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService)
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    List<User> getAllUsers() {
        this.userRepository.findAll()
    }

    Optional<User> getUserById(String id) {
        return userRepository.findById(id)
    }

    Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login)
    }

    User createUser(User user) {
        try {
            return userRepository.save(user)
        } catch (Exception ex) {
            logger.error("error adding user", ex)
            throw ex
        }
    }

    User updateUser(User user) {
        try {
            return userRepository.save(user)
        } catch (Exception ex) {
            logger.error("error modifying user", ex)
            throw ex
        }
    }

    void deleteUser(String id) {
        userRepository.deleteById(id)
    }

    void deleteUserByLogin(String login) {
        userRepository.findByLogin(login).ifPresent { userRepository.deleteById(it.id) }
    }

    void followUser(String userId, String targetUserId, Boolean follow) {
        def user = userRepository.findById(userId).orElseThrow()
        def targetUser = userRepository.findById(targetUserId).orElseThrow()

        follow ? user.subscriptions << targetUserId : user.subscriptions.remove(targetUserId)
        follow ? targetUser.followers << userId : targetUser.followers.remove(userId)

        userRepository.saveAll([user, targetUser])
    }

    void followUserByLogin(String userLogin, String targetUserLogin) {
        def user = userRepository.findByLogin(userLogin).orElseThrow()
        def targetUser = userRepository.findByLogin(targetUserLogin).orElseThrow()

        user.subscriptions << targetUser.id
        targetUser.followers << user.id

        userRepository.saveAll([user, targetUser])
    }

}
