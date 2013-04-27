package org.dandelion.trains

import Types._

trait Position {
  def intersects(other: Position): Boolean
}

case class AtStation(s: Station) extends Position {
  override def intersects(other: Position): Boolean = other match {
    case AtStation(otherStation) => (s == otherStation)
    case _ => false
  }
}

case class AtTrack(from: Station, to: Station) extends Position {
  override def intersects(other: Position): Boolean = other match {
    case AtTrack(fromOther, toOther) =>
      (from == toOther) && (to == fromOther)
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
    intersection(other) != None
  }

  def intersection(other: Trajectory): Option[Position] = {
    if (current.intersects(other.current)) Some(current)
    else if (isFinished && other.isFinished) None
    else rest.intersection(other.rest)
  }
}



