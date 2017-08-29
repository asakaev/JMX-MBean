package ru.tema.producer

import java.time.LocalDateTime
import java.util.UUID

import ru.tema.broker.Message

import scala.util.Random

object MessageGen {

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

  def next(): Message = Message(
    UUID.randomUUID.toString,
    rndElem(badGuys),
    rndElem(topHeroes),
    rndElem(quotes),
    LocalDateTime.now
  )

  private def rndElem[T](xs: Seq[T]): T = xs(Random.nextInt(xs.size))
}