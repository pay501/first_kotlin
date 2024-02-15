package com.scalablescripts.kotlin_blog.repositories

import com.scalablescripts.kotlin_blog.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserModel, Int> {
    fun findByEmail(email :String): UserModel?;
    fun findByPhoneNumber(phoneNumber : String): UserModel?;
}