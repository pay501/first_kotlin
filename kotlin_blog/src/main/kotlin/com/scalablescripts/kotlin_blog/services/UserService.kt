package com.scalablescripts.kotlin_blog.services

import com.scalablescripts.kotlin_blog.Messages.LoginResponse
import com.scalablescripts.kotlin_blog.Messages.SignupResponse
import com.scalablescripts.kotlin_blog.models.UserModel
import com.scalablescripts.kotlin_blog.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class UserService (private val userRepository: UserRepository){
    private val passwordEncoder = BCryptPasswordEncoder();
    fun signup (data : UserModel): Any?{
        try {
            if(
                data.email == "" ||
                data.firstName == "" ||
                data.lastName == "" ||
                data.phoneNumber == "" ||
                data.password == ""
            ){
                return SignupResponse("data can't be null", HttpStatus.BAD_REQUEST.value(),"")
            }

            val isExistByEmail = this.userRepository.findByEmail(data.email);
            if(isExistByEmail != null){
                return SignupResponse("This email has been used.", HttpStatus.BAD_REQUEST.value(),"");
            }

            val isExistByPhoneNumber = this.userRepository.findByPhoneNumber(data.phoneNumber);
            if(isExistByPhoneNumber != null){
                return SignupResponse("This phone number has been used.", HttpStatus.BAD_REQUEST.value(),"")
            }

            data.password = passwordEncoder.encode(data.password);
            return this.userRepository.save(data);

        }catch (e : Exception){
            return SignupResponse("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR.value(),"")
        }
    }

    fun login(data : UserModel, response: HttpServletResponse):Any?{
        if(
            data.email == ""||
            data.password == ""
        ){
            return LoginResponse("Data can't be null.", HttpStatus.BAD_REQUEST.value(), null)
        }

        val user = this.userRepository.findByEmail(data.email)
            ?: return LoginResponse("Email or Password is wrong.", HttpStatus.UNAUTHORIZED.value(), null);

        val isCorrectPassword = passwordEncoder.matches(data.password, user.password )
        if (!isCorrectPassword){
            return LoginResponse("Email or Password is wrong.", HttpStatus.UNAUTHORIZED.value(), null);
        }

        val issuer = user.id.toString();
        val secretKry = ("secretKey");
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date((System.currentTimeMillis() * 1000 + 60) * 24))
            .signWith(SignatureAlgorithm.HS512, secretKry).compact();
        val cookie = Cookie("jwt", jwt);
        cookie.isHttpOnly = true;
        response.addCookie(cookie);
        return LoginResponse("Login successfully.", HttpStatus.OK.value(), null)
    }
}