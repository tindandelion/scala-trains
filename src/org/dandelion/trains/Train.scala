package org.dandelion.trains

class Train(route: List[Any], tracks: Map[(Any, Any), Int] = Map()) {

  val trajectory: Trajectory = Trajectory(route, tracks)

  def collidesWith(those: List[Train]): Boolean =
    those.exists((that) => (this.trajectory.intersects(that.trajectory)))
}
