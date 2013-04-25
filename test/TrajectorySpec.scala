import org.dandelion.trains.{AtTrack, AtStation, Trajectory}
import org.scalatest.FunSpec

class TrajectorySpec extends FunSpec {
  describe("building") {
    it("builds an empty trajectory for an empty station list") {
      assert(Trajectory.build(List()) === List())
    }

    it("builds a single-station trajectory") {
      val route = List('a')
      val traj = Trajectory.build(route)
      assert(traj === List(AtStation('a')))
    }

    it("includes the track info into the trajectory") {
      val route = List('a', 'b')
      val traj = Trajectory.build(route)
      assert(traj === List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
    }

    it("builds a trajectory for a long route") {
      val route = List('a', 'b', 'c')
      val traj = Trajectory.build(route)
      assert(traj === List(AtStation('a'), AtTrack('a', 'b'), AtStation('b'), AtTrack('b', 'c'), AtStation('c')))
    }
  }

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
