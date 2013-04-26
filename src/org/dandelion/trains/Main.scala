package org.dandelion.trains

object Main {
  def hasCollisions(trains: List[Train]): Boolean = trains match {
    case t :: others => t.collidesWith(others) || hasCollisions(others)
    case _ => false
  }
}
