package org.dandelion.trains

object Main {
  type Station = Char
  type Train = List[Station]

  def collide(rw: Railway[Station], trains: List[Train]) = {
    val trajectories = trains.map(rw.buildTrajectory(_))
    hasCollisions(trajectories)
  }

  private def hasCollisions(trjs: List[Trajectory]): Boolean = trjs match {
    case t :: others => intersects(t, others) || hasCollisions(others)
    case _ => false
  }

  def intersects(trj: Trajectory, those: List[Trajectory]): Boolean = {
    those.exists(trj.intersects(_))
  }
}
