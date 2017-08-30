package ru.tema.producer

import org.reactivestreams.{ Publisher, Subscriber, Subscription }

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future }

class MessageSource[T](delay: Duration, g: Generator[T])(implicit ec: ExecutionContext) extends Publisher[T] {
  private var subscriber: Option[Subscriber[_ >: T]] = None

  private lazy val subscription = new Subscription {
    override def request(n: Long): Unit = subscriber.foreach(s => Future {
      Thread.sleep(delay.toMillis)
      s.onNext(g.next())
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
           (implicit ec: ExecutionContext): MessageSource[Message] = new MessageSource(delay, g)
}