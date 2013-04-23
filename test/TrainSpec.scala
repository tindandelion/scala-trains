import org.dandelion.trains.Train
import org.scalatest.FunSpec

class TrainSpec extends FunSpec {
  it("collides with other train if have same start station") {
    val me = new Train(List("a", "b"))
    assert(me.collidesWith(new Train(List("a", "c"))), "collision is expected at the station a")
    assert(!me.collidesWith(new Train(List("c", "d"))), "collision is not expected")
  }

  it("collides with other train if have same final station") {
    val me = new Train(List("a", "b"))
    assert(me.collidesWith(new Train(List("c", "b"))), "collision is expected at the station b")
    assert(!me.collidesWith(new Train(List("c", "d"))), "collision is not expected")
  }

  it("collides with other train at the same intermediate station") {
    val me = new Train(List("a", "b", "c"))
    val other = new Train(List("c", "b", "a"))
    assert(me.collidesWith(other), "collision is expected at the station b")
  }

  it("does not collide with other train if stay at the same station at different time") {
    val me = new Train(List("a", "b", "c"))
    val other = new Train(List("c", "d", "b"))
    assert(!me.collidesWith(other), "collision is not expected")
  }

  it("collides at the track") {
    val me = new Train(List("a", "b"))
    val other = new Train(List("b", "a"))

    assert(me.collidesWith(other), "collision is expected at the track a <-> b")
  }


}
