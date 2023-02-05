package com.pramod.zio

import zio.Console.{printLine, readLine}
import zio.{Console, ZIO, ZIOAppDefault}

object ZPlayGround extends ZIOAppDefault {
  def run = {
    //  val zioSucceed = ZIO.succeed(43)
    //  val zioFailure = ZIO.fail("Something went wrong")

    //  val greeting = for {
    //    _ <- printLine("Hi, what is your name?")
    //    name <-  ZIO.fail("pramod")
    //    _ <- printLine(s"Hello, $name, welcome to zio play ground")
    //  } yield ()

    for {
      aa <- ZIO.fromOption(Option("sdfd")).some(_ =>"aaaaaa")
      _ <- Console.printLine(aa)
    } yield ()

  }
}
