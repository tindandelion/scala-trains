package org.dandelion.trains

class Train(route: List[Any], tracks: Map[(Any, Any), Int] = Map()) {

  val trajectory: Trajectory = new Railway(tracks).buildTrajectory(route)

  def collidesWith(those: List[Train]): Boolean =
    those.exists((that) => (this.trajectory.intersects(that.trajectory)))
}
