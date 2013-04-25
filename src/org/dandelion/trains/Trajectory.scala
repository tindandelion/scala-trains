package org.dandelion.trains

import scala.collection.immutable.HashMap

trait Position {
  def intersects(other: Position): Boolean
}

case class AtStation(station: Any) extends Position {
  override def intersects(other: Position): Boolean = other match {
    case AtStation(otherStation) => (station == otherStation)
    case _ => false
  }
}

case class AtTrack(fromStation: Any, toStation: Any) extends Position {
  override def intersects(other: Position): Boolean = other match {
    case AtTrack(fromOther, toOther) =>
      (fromStation == toOther) && (toStation == fromOther)
    case _ => false
  }
}

class Trajectory(positions: List[Position]) {
  def current = positions.head

  def isFinished = positions.tail.isEmpty

  def rest =
    if (isFinished) new Trajectory(positions)
    else new Trajectory(positions.tail)

  def intersects(other: Trajectory): Boolean = {
    if (current.intersects(other.current)) true
    else if (isFinished && other.isFinished) false
    else rest.intersects(other.rest)
  }

}

object Trajectory {
  type Tracks[T] = Map[(T, T), Int]

  def apply[T](route: List[T]): Trajectory = new Trajectory(build(route))

  def build[T](route: List[T]): List[Position] = build(route, Map[(T, T), Int](), List())

  def build[T](route: List[T], tracks: Tracks[T]): List[Position] = build(route, tracks, List())

  private def build[T](route: List[T], tracks: Tracks[T], res: List[Position]): List[Position] = {
    route match {
      case Nil => res
      case List(s) => res ++ atStation(s)
      case from :: tail => {
        val to = tail.head
        build(tail, tracks, res ++ atStation(from) ++ atTrack(from, to, getLength(from, to, tracks)))
      }
    }
  }


  def getLength[T](from: T, to: T, tracks: Tracks[T]): Int = tracks.getOrElse((from, to), 1)


  private def atStation[T](s: T) = List(AtStation(s))

  private def atTrack[T](from: T, to: T, length: Int) =
    (1 to length).map((i) => AtTrack(from, to))
}
