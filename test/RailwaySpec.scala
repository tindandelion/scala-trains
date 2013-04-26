import org.dandelion.trains.Railway
import org.scalatest.FunSpec

class RailwaySpec extends FunSpec {
  it("returns a distance between stations") {
    val rw = new Railway(Map(('a', 'b') -> 10))

    assert(rw.distanceBetween('a', 'b') === 10, "direct distance")
    assert(rw.distanceBetween('b', 'a') === 10, "reverse distance")
  }

  it("returns distance of 1 by default") {
    val rw = new Railway[Char](Map())
    assert(rw.distanceBetween('a', 'b') === 1)
  }
}
