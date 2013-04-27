package org.dandelion.trains

import scala.Some

case class Collision()

case class Station(id: Char)

case class Train(number: Int, route: List[Station])

object Main {

  def detectCollision(rw: Railway[Station], trains: List[Train]): Option[Collision] = {
    val trajectories = trains.map(t => rw.buildTrajectory(t.route))
    detectCollision(trajectories)
  }

  private def detectCollision(trjs: List[Trajectory]): Option[Collision] = {
    if (trjs.isEmpty) None
    else {
      findIntersection(trjs.head, trjs.tail) match {
        case Some(_) => Some(Collision())
        case _ => detectCollision(trjs.tail)
      }
    }
  }

  private def findIntersection(trj: Trajectory, those: List[Trajectory]): Option[Trajectory] =
    those.find(trj.intersects(_))
}
