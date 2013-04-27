package org.dandelion.trains

case class Collision()

object Main {
  type Station = Char
  type Route = List[Station]

  def detectCollision(rw: Railway[Station], routes: List[Route]): Option[Collision] = {
    val trajectories = routes.map(rw.buildTrajectory(_))
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
