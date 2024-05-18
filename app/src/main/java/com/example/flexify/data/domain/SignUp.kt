package com.example.flexify.data.domain

import com.example.flexify.data.requestmodel.RequestLogin

data class SignUp(
    val email: String,
   val password: String,
    val fullName: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val sex: String,
)

fun SignUp.toRequest():RequestLogin{
    return RequestLogin(
        email=email,
        password=password,
        fullName=fullName,
        age=age,
        weight=weight,
        height=height,
        sex=sex
    )
}