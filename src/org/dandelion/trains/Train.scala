package org.dandelion.trains

object Trajectory {
  def build(route: List[Any]): List[Any] = build(route, List())

  private def build(route: List[Any], res: List[Any]): List[Any] = {
    route match {
      case Nil => res
      case List(s) => res ++ List(s)
      case s1 :: tail => build(tail, res ++ List(s1, (s1, tail.head)))
    }
  }
}

class Train(route: List[Any]) {

  val trajectory = Trajectory.build(route)

  def collidesWith(others: List[Train]): Boolean = others.exists(collidesWith)

  def collidesWith(other: Train): Boolean =
    (other.trajectory, this.trajectory).zipped.exists(comparePositions)

  def comparePositions(p1: Any, p2: Any) = (p1, p2) match {
    case ((s11, s12), (s21, s22)) => (s11 == s22) && (s12 == s21)
    case (s1, s2) => s1 == s2
  }
}
