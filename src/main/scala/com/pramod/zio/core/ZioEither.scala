package com.pramod.zio.core

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ZioEither extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zioEither = ZIO.fromEither(Right("Hello"))
    for {
      value <- zioEither
      _ <- Console.printLine(s"Value $value")
    } yield ()
  }
}

object ZioEither1 extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zioEither = ZIO.fromEither(Left("test exception"))
    for {
      value <- zioEither
      _ <- Console.printLine(s"Value $value")
    } yield ()
  }
}

object ZioEither2 extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zioEither = ZIO.fromEither(Left(new Exception("test test")))
    for {
      value <- zioEither
      _ <- Console.printLine(s"Value $value")
    } yield ()
  }
}

object ZioEither3 extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zioEither = ZIO.left(new Exception("test exception"))
    for {
      value <- zioEither
      _ <- Console.printLine(s"Value $value")
    } yield ()
  }
}

object ZioEither4 extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zioEither = ZIO.right("test val")
    for {
      value <- zioEither
      _ <- Console.printLine(s"Value $value")
    } yield ()
  }
}