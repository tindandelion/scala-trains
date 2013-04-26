package org.dandelion.trains

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

class Trajectory(val positions: List[Position]) {
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

class Railway[T](val tracks: Map[(T, T), Int]) {
  def distanceBetween(one: T, two: T) = tracks.getOrElse((one, two), 1)
}

object Trajectory {
  type Tracks[T] = Map[(T, T), Int]

  def apply[T](route: List[T], tracks: Tracks[T] = Map[(T, T), Int]()) =
    new Trajectory(build(route, new Railway(tracks), List()))

  private def build[T](route: List[T], railway: Railway[T], res: List[Position]): List[Position] = {
    route match {
      case Nil => res
      case List(s) => res ++ atStation(s)
      case from :: tail => {
        val to = tail.head
        build(tail, railway, res ++ atStation(from) ++ atTrack(from, to, railway.distanceBetween(from, to)))
      }
    }
  }

  private def atStation[T](s: T) = List(AtStation(s))

  private def atTrack[T](from: T, to: T, length: Int) =
    (1 to length).map((i) => AtTrack(from, to))
}
