package org.dandelion.trains

class Train(val route: List[String]) {

  def startStation = route.head
  def finishStation = route.last

  def collidesWith(other: Train): Boolean =
    (other.startStation == this.startStation) ||
      (other.finishStation == this.finishStation)

  def collidesWith(others: List[Train]): Boolean = others.exists(collidesWith)
}
