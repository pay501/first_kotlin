package com.scalablescripts.kotlin_blog.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0;

    @Column(nullable = false)
    var firstName = "";

    @Column(nullable = false)
    var lastName = "";

    @Column(unique = true)
    var phoneNumber = "";

    @Column(nullable = false, unique = true)
    var email = "";

    @Column(nullable = false)
    var password = ""
        @JsonIgnore
        get() = field;

}