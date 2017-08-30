package ru.tema.producer

import java.util.concurrent.ExecutorService

import org.reactivestreams.{ Publisher, Subscriber, Subscription }

class MessageSource[T](delay: Long, g: Generator[T])(implicit es: ExecutorService) extends Publisher[T] {
  private var subscriber: Subscriber[_ >: T] = _

  private val subscription = new Subscription {
    override def request(n: Long): Unit = es.execute(() => {
      Thread.sleep(delay)
      subscriber.onNext(g.next())
    })
    override def cancel(): Unit = ???
  }

  override def subscribe(s: Subscriber[_ >: T]): Unit = {
    subscriber = s
    subscriber.onSubscribe(subscription)
  }
}

object MessageSource {
  def apply(delay: Long = 1000, g: Generator[Message] = new MessageGenerator)
           (implicit es: ExecutorService): MessageSource[Message] = new MessageSource(delay, g)
}