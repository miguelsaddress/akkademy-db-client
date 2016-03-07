package com.akkademy

import akka.util.Timeout
import akka.actor.ActorSystem
import akka.pattern.ask
import scala.concurrent.duration._
import com.akkademy.messages._
import scala.util.{Success, Failure}


class SClient(remoteAddress: String){
    private implicit val system = ActorSystem("LocalSystem")
    private val remoteDb = system.actorSelection(s"akka.tcp://akkademy@$remoteAddress/user/akkademy-db")
    private implicit val timeout = Timeout(2 seconds)

    def set(key: String, value: Object) = {
        remoteDb ? SetRequest(key, value)
    }
    
    def setIfNotExists(key: String, value: Object) = {
        remoteDb ? SetIfNotExistsRequest(key, value)
    }

    def get(key: String) = {
        remoteDb ? GetRequest(key)
    }

    def delete(key: String) = {
        remoteDb ? DeleteRequest(key)
    }
}

object Main extends App {
    import scala.concurrent.ExecutionContext.Implicits.global

    val client = new SClient("127.0.0.1:2552")
    println("Setting '123' = 123")
    client.set("123", new Integer(123))

    println("Setting If Not Exists '123' = 456")
    client.setIfNotExists("123", new Integer(456))

    println("Setting If Not Exists '456' = 789")
    client.setIfNotExists("456", new Integer(789))

    client.get("123") onComplete {
        case Success(value) => println("123 returned: " + value)
        case Failure(ex) => println(ex)
    }
    client.get("456") onComplete {
        case Success(value) => println("456 returned: " + value)
        case Failure(ex) => println(ex)
    }

    client.get("789") onComplete {
        case Success(value) => println("789 returned: " + value)
        case Failure(ex) => println(ex.toString + ": couldnt get key 789, it does not exist")
    }

    client.delete("123") onComplete {
        case Success(value) => println("deleted: key 123")
        case Failure(ex) => println(ex.toString + ": couldnt delete key 123, it does not exist")
    }

    client.delete("456") onComplete {
        case Success(value) => println("deleted: key 456")
        case Failure(ex) => println(ex.toString + ": couldnt delete key 456, it does not exist")
    }

    client.delete("789") onComplete {
        case Success(value) => println("deleted: key 789")
        case Failure(ex) => println(ex.toString + ": couldnt delete key 789, it does not exist")
    }
}