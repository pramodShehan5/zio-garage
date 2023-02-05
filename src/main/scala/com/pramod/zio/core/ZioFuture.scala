package com.pramod.zio.core

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault, Console}

import scala.concurrent.Future

object ZioFuture extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val future = Future.successful("Hello Pramod")
    for {
      f <- ZIO.fromFuture(implicit ec =>  future.map(_ => "Good Bye"))
      _ <- Console.printLine(f)
    } yield ()
  }
}

object ZioFuture1 extends ZIOAppDefault{
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val future = Future.failed(new Exception("test exception"))
    for {
      f <- ZIO.fromFuture(implicit ec =>  future.map(_ => "Good Bye"))
      _ <- Console.printLine(f)
    } yield ()
  }
}
