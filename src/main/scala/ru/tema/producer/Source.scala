package ru.tema.producer

import java.util.concurrent.ExecutorService

class Source[T](delay: Long, gen: Generator[T])(implicit es: ExecutorService) {
  def subscribe(f: T => Any): Unit = es.execute(() => {
    while (true) {
      f(gen())
      Thread.sleep(delay)
    }
  })
}

object Source {
  def apply(delay: Long = 1000, gen: Generator[Message] = new MessageGen)
           (implicit es: ExecutorService): Source[Message] = new Source(delay, gen)
}