import org.dandelion.trains.{AtTrack, AtStation, Trajectory}
import org.scalatest.FunSpec

class TrajectorySpec extends FunSpec {
  describe("intersection") {
    it("intersects with other if have same station at the same time") {
      val me = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
      val other = new Trajectory(List(AtStation('a'), AtTrack('a', 'c'), AtStation('c')))

      assert(mutuallyIntersect(me, other))
    }

    it("does not intersect if same station at differerent times") {
      val me = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
      val other = new Trajectory(List(AtStation('c'), AtTrack('c', 'a'), AtStation('a')))

      assert(!me.intersects(other))
      assert(!other.intersects(me))
    }

    it("intersects with other if have same track at the same time") {
      val me = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
      val other = new Trajectory(List(AtStation('b'), AtTrack('b', 'a'), AtStation('a')))

      assert(mutuallyIntersect(me, other))
    }

    it("intersects with other if one's final station is on the way of the other") {
      val me = new Trajectory(List(AtStation('b')))
      val other = new Trajectory(List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))

      assert(mutuallyIntersect(me, other))
    }

    def mutuallyIntersect(one: Trajectory, two: Trajectory) = one.intersects(two) && two.intersects(one)
  }
}
