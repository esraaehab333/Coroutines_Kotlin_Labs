import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

fun produceNumbers () = runBlocking{
    // the capacity
    // capacity=5 -> means that store the five items before waiting
    // Channel.UNLIMITED -> means store the items with not limit (not secure)
    // Channel.CONFLATED -> means save the last value item
    // Channel.RENDEZVOUS -> this must the two side يقابلوا بعض
    val channel = Channel<Int>(capacity = Channel.RENDEZVOUS)
    launch{
        repeat(5){i->
            println("Sending $i")
            channel.send(i)
        }
        channel.close()
    }
    launch{
        repeat(5){
            val result = channel.receive()
            println("Received $result")
        }
    }
    println("Done")
}
fun main(){
    produceNumbers()
}