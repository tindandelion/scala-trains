import org.dandelion.trains.{AtTrack, AtStation}
import org.scalatest.FunSpec

class PositionSpec extends FunSpec {
  describe("AtStation") {
    it("intersects when stations are equal") {
      assert(AtStation('a').intersects(AtStation('a')), "intersection expected")
      assert(!AtStation('a').intersects(AtStation('b')), "intersection is not expected")
    }

    it("does not intersect with track position") {
      assert(!AtStation('a').intersects(AtTrack('a', 'b')), "intersection is not expected")
    }
  }

  describe("AtTrack") {
    it("intersects when points to opposite direction") {
      val me = AtTrack('a', 'b')
      val opposite = AtTrack('b', 'a')
      val same = AtTrack('a', 'b')

      assert(me.intersects(opposite), "intersection of opposite directions expected")
      assert(!me.intersects(same), "intersection of same directions not expected")
    }

    it("does not intersect with station position") {
      assert(!AtTrack('a', 'b').intersects(AtStation('a')), "intersection is not expected")

    }
  }

}
