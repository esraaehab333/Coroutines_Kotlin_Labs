package com.example.coroutine_lab1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

fun evenNumberFlow()= flow<Int>{
    var num = 0
    var counter = 0
    while (counter<20){
        emit(num)
        delay(1000) // every second
        num+=2
        counter++
    }
}
suspend fun main():Unit = coroutineScope {

    evenNumberFlow()
        .take(10) // collect the first ten number
        .collect {
            println("Received: $it")
        }
}