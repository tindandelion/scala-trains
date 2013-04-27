package org.dandelion.trains

class Train[T](route: List[T], railway: Railway[T]) {
  def this(route: List[T], tracks: Map[(T, T), Int] = Map[(T, T), Int]()) =
    this(route, new Railway(tracks))

  val trajectory: Trajectory = railway.buildTrajectory(route)

  def collidesWith(those: List[Train[T]]): Boolean =
    those.exists((that) => (this.trajectory.intersects(that.trajectory)))
}
