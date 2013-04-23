package org.dandelion.trains

class Train(val startStation: String, val finishStation: String) {
  def collidesWith(other: Train): Boolean =
    (other.startStation == this.startStation) ||
      (other.finishStation == this.finishStation)

  def collidesWith(others: List[Train]): Boolean = others.exists(collidesWith)
}
