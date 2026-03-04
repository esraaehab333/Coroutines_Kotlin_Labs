import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.channels.BufferOverflow

fun main() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>(
        replay = 10, // the next subscriber take value number from this
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.SUSPEND // drop the old emits in the buffer
    )
    sharedFlow.emit(1)
    sharedFlow.emit(2)
    sharedFlow.emit(3)
    launch {
        sharedFlow.collect {value->
            println("collector 1")
            println(value)
        }
    }
    sharedFlow.emit(4)
    launch {
        sharedFlow.collect {value->
            println("collector 2")
            println(value)
        }
    }
    val result = sharedFlow.tryEmit(8)
    println("tryEmit success? $result")
}