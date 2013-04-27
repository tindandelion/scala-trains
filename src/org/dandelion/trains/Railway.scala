package org.dandelion.trains

import Types._

class Train(val number: Int, val trajectory: Trajectory)

class MissingTrackError extends Exception

class Railway(tracks: Map[(Station, Station), Int]) {
  def this(tracks: ((Station, Station), Int)*) = this(Map[(Station, Station), Int](tracks: _*))

  def train(number: Int, route: Station*): Train = new Train(number, buildTrajectory(route.toList))

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
      tracks.getOrElse((two, one), { throw new MissingTrackError }))

}
