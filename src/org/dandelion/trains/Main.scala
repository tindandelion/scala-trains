package org.dandelion.trains

import Types._
import scala.Some

case class Collision(t1: Int, t2: Int)

case class Train(number: Int, route: Route)

case class TrainWithTrajectory(train: Train, trajectory: Trajectory)

object Main {

  def detectCollision(rw: Railway, trains: List[Train]): Option[Collision] = {
    val ttrj = trains.map(t => (TrainWithTrajectory(t, rw.buildTrajectory(t.route))))
    detectCollision(ttrj)
  }

  private def detectCollision(trjs: List[TrainWithTrajectory]): Option[Collision] = {
    if (trjs.isEmpty) None
    else {
      val _this = trjs.head
      findIntersection(_this, trjs.tail) match {
        case Some(ttrj) => Some(Collision(_this.train.number, ttrj.train.number))
        case _ => detectCollision(trjs.tail)
      }
    }
  }

  private def findIntersection(_this: TrainWithTrajectory, those: List[TrainWithTrajectory]): Option[TrainWithTrajectory] =
    those.find(t => _this.trajectory.intersects(t.trajectory))
}
