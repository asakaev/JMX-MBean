package ru.tema

import java.lang.management.ManagementFactory
import java.util.concurrent.Executors.newSingleThreadExecutor
import javax.management.ObjectName

import ru.tema.broker.MessageBroker
import ru.tema.producer.MessageSource

object Application extends App {
  implicit val es = newSingleThreadExecutor

  val messageBroker = new MessageBroker

  ManagementFactory.getPlatformMBeanServer.registerMBean(
    messageBroker, new ObjectName("ru.tema:type=MessageBroker")
  )

  MessageSource().subscribe(messageBroker)
}