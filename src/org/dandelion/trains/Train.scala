package org.dandelion.trains



class Train(route: List[Any]) {

  val trajectory = Trajectory.build(route)

  def collidesWith(others: List[Train]): Boolean = others.exists(collidesWith)

  def collidesWith(other: Train): Boolean =
    (other.trajectory, this.trajectory).zipped.exists(_ intersects _)
}
