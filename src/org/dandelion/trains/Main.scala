package org.dandelion.trains

object Main {
  type Station = Char
  type Train1 = List[Station]

  def collide(rw: Railway[Station], trains: List[Train1]) = {
    val t1: List[Train[Station]] = trains.map(route => new Train[Station](route, rw))
    hasCollisions(t1)
  }

  private def hasCollisions(trains: List[Train[Station]]): Boolean = trains match {
    case t :: others => t.collidesWith(others) || hasCollisions(others)
    case _ => false
  }
}
