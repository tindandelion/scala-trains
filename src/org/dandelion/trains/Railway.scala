package org.dandelion.trains

import Types._

class Railway(tracks: Map[(Station, Station), Int]) {

  def buildTrajectory(route: Station*): Trajectory = new Trajectory(build(route.toList, List()))

  def buildTrajectory(route: Route): Trajectory = new Trajectory(build(route, List()))

  private def build(route: Route, res: List[Position]): List[Position] = {
    route match {
      case Nil => res
      case List(s) => res ++ stationSegment(s)
      case from :: tail => {
        val to = tail.head
        build(tail, res ++ trackSegment(from, to))
      }
    }
  }

  private def stationSegment(station: Station): List[Position] = List(AtStation(station))

  private def trackSegment(from: Station, to: Station): List[Position] = {
    val onTrack = (1 to distance(from, to)).map(_ => AtTrack(from, to))
    stationSegment(from) ++ onTrack
  }

  private def distance(one: Station, two: Station) =
    tracks.getOrElse((one, two),
      tracks.getOrElse((two, one), 1))

}

object Railway {
  def apply(tracks: ((Station, Station), Int)*) = new Railway(Map[(Station, Station), Int](tracks: _*))
}
