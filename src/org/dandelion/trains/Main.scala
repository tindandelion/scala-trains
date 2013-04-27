package org.dandelion.trains

import scala.Some


case class Collision(_1: Train, _2: Train, position: Position)


object Main {

  def detectCollision(trains: List[Train]): Option[Collision] =
    if (trains.isEmpty) None
    else {
      detectCollision(trains.head, trains.tail) match {
        case None => detectCollision(trains.tail)
        case found => found
      }
    }

  private def detectCollision(_this: Train, those: List[Train]): Option[Collision] =
    if (those.isEmpty) None
    else {
      val that = those.head
      _this.trajectory.intersection(that.trajectory) match {
        case Some(position) => Some(Collision(_this, that, position))
        case _ => detectCollision(_this, those.tail)
      }
    }
}
