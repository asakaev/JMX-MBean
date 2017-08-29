package ru.tema.producer

import java.util.concurrent.ExecutorService

class Source[A, B](delay: Long, gen: Generator[A])(implicit es: ExecutorService) {
  def subscribe(f: A => B): Unit = es.execute(() => {
    while (true) {
      f(gen())
      Thread.sleep(delay)
    }
  })
}

object Source {
  def apply(delay: Long = 1000, gen: Generator[Message] = new MessageGen)
           (implicit es: ExecutorService): Source[Message, Unit] =
    new Source(delay, gen)
}