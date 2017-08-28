package ru.tema

import java.util.{ Timer, TimerTask }


object TimeUtils {

  def setInterval(f: () => Unit, delay: Long): Timer = {
    val timer = new Timer
    val task = new TimerTask {
      override def run() = f()
    }
    timer.schedule(task, delay, delay)
    timer
  }

  def setTimeout(f: () => Unit, delay: Long): Unit = {
    val timer = new Timer
    val task = new TimerTask {
      override def run() = {
        f()
        timer.cancel()
      }
    }
    timer.schedule(task, delay)
  }

  def clearInterval(timer: Timer) = timer.cancel()
}