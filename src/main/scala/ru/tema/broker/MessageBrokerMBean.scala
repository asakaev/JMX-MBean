package ru.tema.broker

import java.util

trait MessageBrokerMBean {

  // JMX attributes
  def getQueuedMessagesCount: Int
  def getSendersCount: Int
  def getRecipientsCount: Int

  // JMX operation
  def senders: util.Set[String]
  def recipients: util.Set[String]
  def dropQueue(): Int
  def lastMessages(n: Int): util.List[String]
}