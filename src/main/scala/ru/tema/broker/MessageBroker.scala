package ru.tema.broker

import java.util

import ru.tema.producer.Message

import scala.collection.JavaConverters._
import scala.collection.concurrent.TrieMap

class MessageBroker extends MessageBrokerMBean {
  private val messages = new TrieMap[Int, Message]

  def send(message: Message): Unit = messages.update(message.id, message)

  override def getQueuedMessagesCount: Int = messages.size
  override def getRecipientsCount: Int = uniqueRecipients.size
  override def getSendersCount: Int = uniqueSenders.size
  override def senders: util.Set[String] = toJavaSet(uniqueSenders)
  override def recipients: util.Set[String] = toJavaSet(uniqueRecipients)

  override def lastMessages(n: Int): util.List[String] =
    toJavaList(messages.values.toSeq.sortBy(_.ctime).reverse.take(n).map(_.text))

  override def dropQueue(): Int = {
    val dropped = messages.size
    messages.clear()
    dropped
  }

  private def uniqueRecipients: Set[String] =
    messages.collect { case (_, v) => v.to }.toSet

  private def uniqueSenders: Set[String] =
    messages.collect { case (_, v) => v.from }.toSet

  private def toJavaSet[T](xs: Set[T]) = new util.HashSet[T](xs.asJava)
  private def toJavaList[T](xs: Seq[T]) = new util.Vector[T](xs.asJava)
}