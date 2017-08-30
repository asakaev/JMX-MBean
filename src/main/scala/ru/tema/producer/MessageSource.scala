package ru.tema.producer

import java.util.concurrent.ExecutorService

import org.reactivestreams.{ Publisher, Subscriber, Subscription }

import scala.concurrent.duration._

class MessageSource[T](delay: Duration, g: Generator[T])(implicit es: ExecutorService) extends Publisher[T] {
  private var subscriber: Option[Subscriber[_ >: T]] = None

  private lazy val subscription = new Subscription {
    override def request(n: Long): Unit = es.execute(() => {
      subscriber.foreach(s => {
        Thread.sleep(delay.toMillis)
        s.onNext(g.next())
      })
    })
    override def cancel(): Unit = subscriber = None
  }

  override def subscribe(s: Subscriber[_ >: T]): Unit = {
    subscriber match {
      case Some(_) => s.onError(new Error("subscribers.limit"))
      case None =>
        subscriber = Some(s)
        subscriber.foreach(_.onSubscribe(subscription))
    }
  }
}

object MessageSource {
  def apply(delay: Duration = 1.second, g: Generator[Message] = new MessageGenerator)
           (implicit es: ExecutorService): MessageSource[Message] = new MessageSource(delay, g)
}