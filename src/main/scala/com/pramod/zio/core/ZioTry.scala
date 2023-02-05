package com.pramod.zio.core

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import scala.util.Try

object ZioTry extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val zioTry = ZIO.fromTry(Try(32/0))
    for {
      value <- zioTry
      _ <- Console.printLine(s"Value $value")
    } yield ()
  }
}
