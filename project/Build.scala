import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

object Build extends sbt.Build {

  lazy val project = Project(
    id = "blob",
    base = file("."),
    settings = Project.defaultSettings ++ packageArchetype.java_application ++ Seq(
      name                  := "bridgewater-test",
      organization          := "cakesolutions",
      maintainer            := "Carl Pulley <carlp@cakesolutions.net>",
      version               := "0.1",
      scalaVersion          := "2.11.3",
      scalacOptions         := Seq("-deprecation", "-feature", "-encoding", "utf8"),
      libraryDependencies   ++= Dependencies()
    )
  )

  object Dependencies {

    object Versions {
      val scalacheck = "1.11.5"
      val scalatest  = "2.1.7"
      val specs2     = "2.4.2-scalaz-7.0.6"
    }

    val compileDependencies = Seq(
    )

    val testDependencies = Seq(
      "org.scalatest"     %% "scalatest"    % Versions.scalatest % "test",
      "org.scalacheck"    %% "scalacheck"   % Versions.scalacheck % "test",
      "org.specs2"        %% "specs2"       % Versions.specs2 % "test"
    )

    def apply(): Seq[ModuleID] = compileDependencies ++ testDependencies

  }

}
