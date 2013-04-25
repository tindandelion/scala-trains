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
  def build(route: List[Any]): List[Position] = build(route, List())

  private def build(route: List[Any], res: List[Position]): List[Position] = {
    route match {
      case Nil => res
      case List(s) => res ++ List(AtStation(s))
      case s1 :: tail => build(tail, res ++ List(AtStation(s1), AtTrack(s1, tail.head)))
    }
  }
}
