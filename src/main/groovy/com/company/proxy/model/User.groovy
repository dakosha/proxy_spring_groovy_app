package com.company.proxy.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime

@Document(collection = "_users")
class User {

    @Id
    String id

    @Indexed(unique = true)
    String login

    String name
    String lastName
    LocalDateTime birthDate

    Set<String> subscriptions = []
    Set<String> followers = []

}
