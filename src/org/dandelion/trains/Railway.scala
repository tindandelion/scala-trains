package org.dandelion.trains

class Railway[T](tracks: Map[(T, T), Int]) {

  def buildTrajectory(route: T*): Trajectory = new Trajectory(build(route.toList, List()))

  def buildTrajectory(route: List[T]): Trajectory = new Trajectory(build(route, List()))

  private def build(route: List[T], res: List[Position]): List[Position] = {
    route match {
      case Nil => res
      case List(s) => res ++ stationSegment(s)
      case from :: tail => {
        val to = tail.head
        build(tail, res ++ trackSegment(from, to))
      }
    }
  }

  private def stationSegment(station: T): List[Position] = List(AtStation(station))

  private def trackSegment(from: T, to: T): List[Position] = {
    val onTrack = (1 to distance(from, to)).map(_ => AtTrack(from, to))
    stationSegment(from) ++ onTrack
  }

  private def distance(one: T, two: T) =
    tracks.getOrElse((one, two),
      tracks.getOrElse((two, one), 1))

}

object Railway {
  def apply[T](tracks: ((T, T), Int)*) = new Railway[T](Map[(T, T), Int](tracks: _*))
}
