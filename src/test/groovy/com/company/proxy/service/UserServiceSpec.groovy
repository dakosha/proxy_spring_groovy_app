package com.company.proxy.service

import com.company.proxy.model.User
import com.company.proxy.repository.UserRepository
import spock.lang.Specification
import spock.lang.Subject

class UserServiceSpec extends Specification {

    def userRepository = Mock(UserRepository)
    @Subject
    def userService = new UserService(userRepository)

    def "should return user when found by ID"() {
        given:
        def user = new User(id: "123", login: "dauren@gmail.com")
        userRepository.findById("123") >> Optional.of(user)

        when:
        def result = userService.getUserById("123")

        then:
        result.get() == user
    }

    def "should throw exception when user not found"() {
        given:
        userRepository.findById("999") >> Optional.empty()

        when:
        def result = userService.getUserById("999")

        then:
        result == Optional.empty()
    }

    def "should return all users"() {
        given:
        def users = [
                new User(id: "123", name: "Alice"),
                new User(id: "456", name: "Bob")
        ]
        userRepository.findAll() >> users

        when:
        def result = userService.getAllUsers()

        then:
        result.size() == 2
        result*.id == ["123", "456"]
    }

}
