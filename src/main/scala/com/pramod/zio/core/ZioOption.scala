package com.pramod.zio.core

import com.pramod.zio.core.ZioOption6.parseInt
import zio.{Console, IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ZioOption extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zoOption: IO[Option[Nothing], Int] = ZIO.fromOption(Some(2))
    val zoOption2: IO[String, Int] = zoOption.mapError(_ => "No value")
    for {
      option <- zoOption2
      _ <- Console.printLine(option)
    } yield ()
  }
}

object ZioOption1 extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zoOption: IO[Option[Nothing], Int] = ZIO.fromOption(Option(2))
    val zoOption2: IO[String, Int] = zoOption.mapError(_ => "No value")
    for {
      option <- zoOption2
      _ <- Console.printLine(option)
    } yield ()
  }
}

object ZioOption2 extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zoOption: IO[Option[Nothing], Int] = ZIO.fromOption(None)
    val zoOption2: IO[String, Int] = zoOption.mapError(_ => "No value")
    for {
      option <- zoOption2
      _ <- Console.printLine(option)
    } yield ()
  }
}

object ZioOption3 extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zoOption: IO[Option[Nothing], Int] = ZIO.fromOption(None)
//    val zoOption2: IO[String, Int] = zoOption
    for {
      option <- zoOption.asSomeError
      _ <- Console.printLine(option)
    } yield ()
  }
}

object ZioOption4 extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val r1: ZIO[Any, Throwable, Int] = ZIO.getOrFail(parseInt("1.2"))
    for {
      r <- r1
      _ <-  Console.printLine(r)
    } yield ()
  }

  def parseInt(input: String): Option[Int] = input.toIntOption
}

object ZioOption5 extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- Console.printLine("Please input number")
      input <- Console.readLine
      r <- readInput(input)
      _ <- Console.printLine(s"Input number is valid number $r")
    } yield ()
  }

  def parseInt(input: String): Option[Int] = input.toIntOption

  def readInput(input: String): ZIO[Any, Unit, Int] =
    ZIO.getOrFailUnit(parseInt(input))
}

object ZioOption6 extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- Console.printLine("Please input number")
      input <- Console.readLine
      r <- readInput(input)
      _ <-  Console.printLine(r)
    } yield ()
  }

  def parseInt(input: String): Option[Int] = input.toIntOption

  def readInput(input: String): ZIO[Any, Throwable, Int] =
    ZIO.getOrFailWith(new NumberFormatException("invalid input"))(parseInt("1.2"))
}