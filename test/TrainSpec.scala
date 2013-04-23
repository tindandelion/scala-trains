import org.dandelion.trains.Train
import org.scalatest.FunSpec

class TrainSpec extends FunSpec {
  it("collides with other train if have same start station") {
    val me = new Train("a", "b")
    assert(me.collidesWith(new Train("a", "c")), "collision is expected")
    assert(!me.collidesWith(new Train("c", "d")), "collision is not expected")
  }

  it("collides with other train if have same finish station") {
    val me = new Train("a", "b")
    assert(me.collidesWith(new Train("c", "b")), "collision is expected")
    assert(!me.collidesWith(new Train("c", "d")), "collision is not expected")
  }
}
