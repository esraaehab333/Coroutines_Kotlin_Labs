import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
fun getNumbers()=flow<Int>{
    for(i in 1..10){
        delay(1000)
        emit(i) // print the number
    }
}
suspend fun main():Unit = coroutineScope {
    // without the withContext()
    getNumbers()
        .filter { i-> i>5 }
        .map { i -> i*2 }
        .collect{
            println("The Number is : $it")
        }
    // with the withContext() using the flowOn
    // Main -> changing on the UI
    // Default -> heavyCalculation
    // IO -> the input output and working on the background thread
    println("Map running on ${Thread.currentThread().name}")
    getNumbers()
        .flowOn(Dispatchers.IO)
        .map {
            println("Map running on ${Thread.currentThread().name}")
            it * 20000
        }
        .flowOn(Dispatchers.Default)
        .collect{
            println("The Number is : $it")
        }
    getNumbers() // is that working on the io !!
        .map {
            println("Map running on ${Thread.currentThread().name}")
            it * 20000
        }
        .flowOn(Dispatchers.Default)
        .flowOn(Dispatchers.IO)
        .collect{
            println("The Number is : $it")
        }
}