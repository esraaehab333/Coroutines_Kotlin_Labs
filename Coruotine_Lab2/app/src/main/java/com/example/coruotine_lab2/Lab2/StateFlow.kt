// StateFlowDemo.kt

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val stateFlow = MutableStateFlow("Initial Value")
    launch {
        stateFlow.collect { value ->
            println("Collector 1")
            println(value)
        }
    }
    stateFlow.value = "First Update"
    stateFlow.value = "Second Update"
    launch {
        stateFlow.collect { value ->
            println("Collector 2")
            println(value)
        }
    }
    stateFlow.value = "Third Update"
    delay(500)
}