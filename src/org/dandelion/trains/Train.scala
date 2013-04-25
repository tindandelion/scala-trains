package org.dandelion.trains

class Train(route: List[Any]) {

  val trajectory: Trajectory = Trajectory(route)

  def collidesWith(those: List[Train]): Boolean =
    those.exists((that) => (this.trajectory.intersects(that.trajectory)))
}
