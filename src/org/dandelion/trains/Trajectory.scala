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

object Trajectory {
  type Trajectory = List[Position]

  def build(route: List[Any]): Trajectory = build(route, List())
  def intersect(me: Trajectory, other: Trajectory): Boolean = (me, other) match {
    case (_, Nil) => false
    case (Nil, _) => false
    case (List(myPos), otherPos :: otherTail) => myPos.intersects(otherPos) || intersect(me, otherTail)
    case (myPos :: myTail, List(otherPos)) => myPos.intersects(otherPos) || intersect(myTail, other)
    case (pos1 :: tail1, pos2 :: tail2) => pos1.intersects(pos2) || intersect(tail1, tail2)
    case _ => throw new Error("Can not happen")
  }


  private def build(route: List[Any], res: Trajectory): Trajectory = {
    route match {
      case Nil => res
      case List(s) => res ++ List(AtStation(s))
      case s1 :: tail => build(tail, res ++ List(AtStation(s1), AtTrack(s1, tail.head)))
    }
  }
}
