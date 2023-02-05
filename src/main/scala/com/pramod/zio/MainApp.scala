package com.pramod.zio

import zio.{Console, Duration, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, durationInt}

import java.io.IOException
import java.util.concurrent.TimeUnit

object MainApp extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    Console.printLine("Hello World")
  }
}

object HelloWorld extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Int] = {
    Console.printLine("Hello World") as 0 // Console.printLine("Hello World").map(_ => 0)
  }
}

object PrintSequence extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    Console.printLine("Hello") *> Console.printLine("World")
    //Console.printLine("Hello").zipLeft(Console.printLine("World"))
  }
}

object ErrorRecovery extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Nothing, Int] = {
//    failed as 0 orElse(ZIO.succeed(1))
    failed fold(_ => 1, _ => 0)
    //(failed as 0).catchAllCause(cause => Console.printLine(s"Exception ${cause.prettyPrint}") as 1)
  }

  val failed = Console.printLine("Hello world!") *> ZIO.fail("test failure")*> Console.printLine("My name is Pramod")
}

object Looping extends ZIOAppDefault {

  def repeat[R, E, A](n: Int)(effect: ZIO[R, E, A]): ZIO[R, E, A] =
    if(n <= 1) effect
    else effect *> repeat(n-1)(effect)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    repeat(10)(Console.printLine("Hello World"))
  }
}

object PromptName extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- Console.printLine("What is your name?")
      name <- Console.readLine
      _ <- Console.printLine(s"Hello $name")
    } yield ()
  }
}

object AlarmAppImproved extends ZIOAppDefault {

  def toDouble(s: String): Either[NumberFormatException, Double] =
    try Right(s.toDouble) catch {
      case e: NumberFormatException => Left(e)
    }

  lazy val getAlarmDuration: ZIO[Console, IOException, Duration] = {
    def parseDuration(input: String): Either[NumberFormatException, Duration] = toDouble(input).map(double => Duration((double * 1000).toLong, TimeUnit.MILLISECONDS))

    val fallback = Console.printLine("You didn't enter the number of seconds") *> getAlarmDuration

    for {
      _ <- Console.printLine("Please enter the number of seconds to sleep")
      input <- Console.readLine
      duration <- ZIO.fromEither(parseDuration(input)) orElse (fallback)
    } yield duration
  }

  override def run: ZIO[Any, Any, Any] = {
//    lazy val getAlarmDuration: ZIO[Console, IOException, Duration] = {
      def parseDuration(input: String): Either[NumberFormatException, Duration] = toDouble(input).map(double => Duration((double * 1000).toLong, TimeUnit.MILLISECONDS))

      //val fallback = Console.printLine("You didn't enter the number of seconds") *> getAlarmDuration

      //    for {
      //      duration <- getAlarmDuration
      //      _ <- ZIO.sleep(duration)
      //      _ <- Console.printLine("Time to wakeup")
      //    } yield ()
      for {
        _ <- Console.printLine("Please enter the number of seconds to sleep")
        input <- Console.readLine
        duration <- ZIO.fromEither(parseDuration(input)) //orElse (fallback)
        fiber <- (Console.printLine(".") *> ZIO.sleep(1.second)).forever.fork
        _ <- ZIO.sleep(duration)
        _ <- Console.printLine("Time to wakeup")
//        _ <- fiber.interrupt
      } yield duration
//    }
  }
}