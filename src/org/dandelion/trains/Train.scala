package org.dandelion.trains

class Train(route: List[Any]) {

  val trajectory = Trajectory.build(route)

  def collidesWith(others: List[Train]): Boolean = others.exists(collidesWith)

  def collidesWith(other: Train): Boolean = Trajectory.intersect(this.trajectory, other.trajectory)
}
