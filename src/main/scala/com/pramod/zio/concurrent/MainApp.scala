package com.pramod.zio.concurrent

import jdk.jshell.spi.ExecutionControl.UserException
import zio._
import zio._

object MainApp extends ZIOAppDefault {
  val barJob: ZIO[Any, Nothing, Long] =
    ZIO
      .debug("Bar: still running!")
      .repeat(Schedule.fixed(1.seconds))

  val fooJob: ZIO[Any, Nothing, Unit] =
    for {
      _ <- ZIO.debug("Foo: started!")
      _ <- barJob.fork
      _ <- ZIO.sleep(5.seconds)
      _ <- ZIO.debug("Foo: finished!")
    } yield ()


  def printThread = s"[${Thread.currentThread().getName}]"

  def run = {
    val list = List(1,2,3,4,5,6)
    for {
      fiber1 <- ZIO.succeed("Hi!").debug(printThread).fork
      fiber2 <- ZIO.succeed("Bye!").debug(printThread).fork
      fiber = fiber1.zip(fiber2)
      tuple <- fiber.join.debug(printThread)
      _ <- ZIO.foreachPar(list)(getUserById).debug(printThread)
      _ <-  getUserFromCache().race(getUserFromDB).debug(printThread)
    } yield tuple


  }

  import scala.concurrent.Future

  lazy val future = Future.successful("Hello!")

  def zfuture(userId: Int): ZIO[Any, Throwable, String] =
    ZIO.fromFuture { implicit ec =>
      future.map(_ => "Goodbye!")
    }

  def getUserById(userId: Int): ZIO[Any, Throwable, String] = {
    if(userId == 3) {
      throw new Exception("tesfdsfdsd")
    } else {
      ZIO.succeed("sdfdf")
    }
  }

  def getUserFromCache(): ZIO[Any, Throwable, List[Int]] =
    ZIO.sleep(Duration.fromNanos(31)) *> ZIO.succeed(List(1,2,3,4,5,8))

  def getUserFromDB(): ZIO[Any, Throwable, List[Int]] =
    ZIO.sleep(Duration.fromNanos(30)) *> ZIO.succeed(List(11, 21, 31, 41, 51, 81))
}