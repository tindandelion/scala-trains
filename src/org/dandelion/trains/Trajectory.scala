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
  def apply(route: List[Any]): Trajectory = new Trajectory(build(route))

  def build(route: List[Any]): List[Position] = build(route, List())

  def build(route: List[Any], tracks: Any): List[Position] = build(route)

  private def build(route: List[Any], res: List[Position]): List[Position] = {
    route match {
      case Nil => res
      case List(s) => res ++ List(AtStation(s))
      case s1 :: tail => build(tail, res ++ List(AtStation(s1), AtTrack(s1, tail.head)))
    }
  }
}
