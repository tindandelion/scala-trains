package org.dandelion.trains

import Types._
import scala.Some

case class Collision()

case class Train(number: Int, route: Route)

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
