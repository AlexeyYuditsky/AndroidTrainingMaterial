package com.alexeyyuditsky.test.contract

import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.model.Cat
import com.alexeyyuditsky.test.model.CatsService

fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract {

    val catsService: CatsService

    fun launchCatDetailsScreen(cat: Cat)

}