package com.pramod.zio

import zio.{Console, IO, Scope, UIO, ZIO, ZIOAppArgs, ZIOAppDefault}

object ZioCore extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val s1: UIO[Int] = ZIO.succeed(22)
    val f1 = ZIO.fail("test exception")
    //Console.printLine(s1)
    for {
      s <-  s1
      _ <- Console.printLine(s)
    } yield ()




  }
}
