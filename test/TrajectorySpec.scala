import org.dandelion.trains.{AtTrack, AtStation, Trajectory}
import org.scalatest.FunSpec

class TrajectorySpec extends FunSpec {
  describe("intersection") {
    it ("returns None if no intersection is found") {
      val me = new Trajectory(List(AtStation('a')))
      val other = new Trajectory(List(AtStation('b')))

      assert(me.intersection(other) === None)
    }

    it("intersects with other if have same station at the same time") {
      val me = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
      val other = new Trajectory(List(AtStation('a'), AtTrack('a', 'c'), AtStation('c')))

      assert(me.intersection(other) === Some(AtStation('a')))
    }

    it("does not intersect if same station at differerent times") {
      val me = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
      val other = new Trajectory(List(AtStation('c'), AtTrack('c', 'a'), AtStation('a')))

      assert(me.intersection(other) === None)
    }

    it("intersects with other if have same track at the same time") {
      val me = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
      val other = new Trajectory(List(AtStation('b'), AtTrack('b', 'a'), AtStation('a')))

      assert(me.intersection(other) === Some(AtTrack('a', 'b')))
    }

    it("intersects with other if one's final station is on the way of the other") {
      val me = new Trajectory(List(AtStation('b')))
      val other = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))

      assert(me.intersection(other) === Some(AtStation('b')))
    }
  }
}
