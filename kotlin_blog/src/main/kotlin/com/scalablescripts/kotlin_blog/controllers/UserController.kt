package com.scalablescripts.kotlin_blog.controllers

import com.scalablescripts.kotlin_blog.Messages.LoginResponse
import com.scalablescripts.kotlin_blog.dtos.LoginDTO
import com.scalablescripts.kotlin_blog.dtos.SignupDTO
import com.scalablescripts.kotlin_blog.models.UserModel
import com.scalablescripts.kotlin_blog.services.UserService
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello. Spy!"
    }

    @PostMapping("/signup")
    fun signup(@RequestBody body: SignupDTO): ResponseEntity<Any> {
        val user = UserModel();
        user.firstName = body.firstName;
        user.lastName = body.lastName;
        user.phoneNumber = body.phoneNumber;
        user.email = body.email;
        user.password = body.password;
        val result = this.userService.signup(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = UserModel();
        user.email = body.email;
        user.password = body.password;

        val result = this.userService.login(user, response);
        return ResponseEntity.ok(result);
    }

    @GetMapping("checkSession")
    fun checkSession(@CookieValue("jwt") jwt: String?):ResponseEntity<Any>{
        if(jwt == null){
            return ResponseEntity.ok(LoginResponse("Unauthorized", HttpStatus.UNAUTHORIZED.value(), ""))
        }

        val data = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(jwt).body
        return ResponseEntity.ok(LoginResponse("Authorized", HttpStatus.OK.value(),data))
    }
}