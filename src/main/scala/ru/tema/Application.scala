package ru.tema

import ru.tema.TimeUtils.{ clearInterval, setInterval, setTimeout }

object Application extends App {
  def beep() = println("Beep")

  val timer = setInterval(beep, 1000)
  setTimeout(() => clearInterval(timer), 5000)

  println("App started")
}