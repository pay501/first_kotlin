package com.scalablescripts.kotlin_blog.Messages

class SignupResponse(
    public val message: String,
    public val status: Int,
    public val body: Any?
) {
}