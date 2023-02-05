package com.pramod.zio

import zio.CanFail.canFailAmbiguous1
import zio.{Console, Schedule, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, durationInt}

object DefectNoHandledEx extends ZIOAppDefault{

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- runSomePeriodicJob
        //.catchAllCause(e => Console.printLine(s"Job failed with $e"))
      //  .repeat(Schedule.spaced(1.seconds))
       // .forkDaemon
      _ <- Console.printLine("do other stuffs..")
        .repeat(Schedule.spaced(1.seconds))
    } yield ()
  }
  private def runSomePeriodicJob ={
    ZIO.succeed(throw new RuntimeException("unexpected exception")) *> ZIO.succeed(println("running jobs..."))
  }
}
