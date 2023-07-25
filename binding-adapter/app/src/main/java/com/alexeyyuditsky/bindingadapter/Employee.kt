package com.alexeyyuditsky.bindingadapter

data class Employee(
    val id: Long,
    var name: String,
    val address: String,
    val age: Int,
    val hobbies:List<String>,
    val avatarUrl: String = "https://secretmag.ru/thumb/890x0/filters:quality(75):no_upscale()/imgs/2022/09/23/13/5597441/facef1b4d81077d005f8c7b8fc1ecf01c309f638.jpg"
)

data class Department(
    val id: Long,
    val name: String
)