package ru.tema.producer

import java.util.concurrent.ExecutorService

trait DataStream[T] {
  def next(): T
}

class Source[T](delay: Long, ds: DataStream[T])(implicit es: ExecutorService) {
  def subscribe(f: T => Any): Unit = es.execute(() => {
    while (true) {
      f(ds.next())
      Thread.sleep(delay)
    }
  })
}

object Source {
  def apply(delay: Long = 1000, ds: DataStream[Message] = new MessageStream)
           (implicit es: ExecutorService): Source[Message] = new Source(delay, ds)
}