import org.dandelion.trains.Trajectory
import org.scalatest.FunSpec

class TrajectorySpec extends FunSpec {
  describe("building") {
    it("builds an empty trajectory for an empty station list") {
      assert(Trajectory.build(List()) === List())
    }

    it("builds a single-station trajectory") {
      val route = List('a')
      val traj = Trajectory.build(route)
      assert(traj === List('a'))
    }

    it("includes the track info into the trajectory") {
      val route = List('a', 'b')
      val traj = Trajectory.build(route)
      assert(traj === List('a', ('a', 'b'), 'b'))
    }

    it("builds a trajectory for a long route") {
      val route = List('a', 'b', 'c')
      val traj = Trajectory.build(route)
      assert(traj === List('a', ('a', 'b'), 'b', ('b', 'c'), 'c'))
    }
  }
}
