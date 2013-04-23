package org.dandelion.trains

class Train(val route: List[String]) {
  def collidesWith(others: List[Train]): Boolean = others.exists(collidesWith)

  def collidesWith(other: Train): Boolean =
    (other.route, this.route).zipped.exists(_ == _)
}
