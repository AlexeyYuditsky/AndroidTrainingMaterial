package com.alexeyyuditsky.test.model

import com.github.javafaker.Faker

class CatsService {

    val cats: List<Cat> = (0..30).map {
        Cat(
            id = it,
            name = Faker.instance().cat().name(),
            description = Faker.instance().lorem().paragraph(10)
        )
    }

}