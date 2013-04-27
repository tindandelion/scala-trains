package org.dandelion.trains

import Types._

trait Position {
  def intersects(other: Position): Boolean
}

case class AtStation(station: Station) extends Position {
  override def intersects(other: Position): Boolean = other match {
    case AtStation(otherStation) => (station == otherStation)
    case _ => false
  }
}

case class AtTrack(fromStation: Station, toStation: Station) extends Position {
  override def intersects(other: Position): Boolean = other match {
    case AtTrack(fromOther, toOther) =>
      (fromStation == toOther) && (toStation == fromOther)
    case _ => false
  }
}

class Trajectory(val positions: List[Position]) {
  def current = positions.head

  def isFinished = positions.tail.isEmpty

  def rest =
    if (isFinished) new Trajectory(positions)
    else new Trajectory(positions.tail)

  def intersects(other: Trajectory): Boolean = {
    if (current.intersects(other.current)) true
    else if (isFinished && other.isFinished) false
    else rest.intersects(other.rest)
  }
}



