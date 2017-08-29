package ru.tema.producer

import java.util.concurrent.ExecutorService

import ru.tema.broker.Message
import ru.tema.producer.MessageGen.next

class Source(delay: Long)(implicit es: ExecutorService) {
  def subscribe(handler: Message => Unit): Unit = es.execute(() => {
    while (true) {
      handler(next())
      Thread.sleep(delay)
    }
  })
}

object Source {
  def apply(delay: Long = 1000)(implicit es: ExecutorService): Source =
    new Source(delay)
}