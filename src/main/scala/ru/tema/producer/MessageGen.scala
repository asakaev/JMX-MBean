package ru.tema.producer

import java.time.LocalTime
import java.util.concurrent.atomic.AtomicInteger

import scala.util.Random

case class Message(id: Int, from: String, to: String, text: String, ctime: LocalTime)

trait Generator[T] {
  def apply(): T
}

class MessageGen extends Generator[Message] {

  private val id = new AtomicInteger(1)
  private val topHeroes = Seq("Spider-Man", "Iron Man", "Deadpool", "Doctor Strange", "Hulk", "Thor")
  private val badGuys = Seq("Ultron", "Loki", "Red Skull", "Mystique", "Thanos", "Ronan", "Magneto")

  private val quotes = Seq(
    "We Have Left Humanity Behind",
    "Don't Ever Tell Me There's No Way",
    "Myeuh-muh? What's Myeuh-muh?",
    "Until Such Time As The World Ends...",
    "I'm Always Angry",
    "You Are, All Of You, Beneath Me",
    "There Will Never Be A Wiser King Than You",
    "I'm Working",
    "I Am Iron Man",
    "I'm Just A Kid From Brooklyn"
  )

  def apply(): Message = Message(
    id.getAndIncrement(),
    rndElem(badGuys),
    rndElem(topHeroes),
    rndElem(quotes),
    LocalTime.now
  )

  private def rndElem[T](xs: Seq[T]): T = xs(Random.nextInt(xs.size))
}