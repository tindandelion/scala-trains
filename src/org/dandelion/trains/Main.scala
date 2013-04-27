package org.dandelion.trains

import scala.Some


case class Collision(_1: Train, _2: Train)


object Main {

  def detectCollision(rw: Railway, trains: List[Train]): Option[Collision] = {
    detectCollision(trains)
  }

  def detectCollision(trains: List[Train]): Option[Collision] = {
    if (trains.isEmpty) None
    else {
      val head = trains.head
      findIntersection(head, trains.tail) match {
        case Some(other) => Some(Collision(head, other))
        case _ => detectCollision(trains.tail)
      }
    }
  }

  private def findIntersection(_this: Train, those: List[Train]): Option[Train] =
    those.find(t => _this.trajectory.intersects(t.trajectory))
}
