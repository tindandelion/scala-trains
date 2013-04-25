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
  }
}

object Trajectory {
  type Trajectory = List[Position]

  def build(route: List[Any]): Trajectory = build(route, List())
  def intersect(t1: Trajectory, t2: Trajectory) =
    (t1, t2).zipped.exists(_ intersects _)

  private def build(route: List[Any], res: Trajectory): Trajectory = {
    route match {
      case Nil => res
      case List(s) => res ++ List(AtStation(s))
      case s1 :: tail => build(tail, res ++ List(AtStation(s1), AtTrack(s1, tail.head)))
    }
  }
}
