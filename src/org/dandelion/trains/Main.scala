package org.dandelion.trains

import scala.Some


case class Collision(_1: Train, _2: Train, position: Position)


object Main {
  def detectCollision(trains: List[Train]): Option[Collision] = {
    val collisions = for (List(a, b) <- trains.combinations(2);
         collisionPosition = a.trajectory.intersection(b.trajectory)
         if (collisionPosition != None)) yield Collision(a, b, collisionPosition.get)

    if (collisions.isEmpty) None
    else Some(collisions.next())
  }
}
