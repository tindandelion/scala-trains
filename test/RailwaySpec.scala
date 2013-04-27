import org.dandelion.trains._
import org.dandelion.trains.AtStation
import org.dandelion.trains.AtTrack
import org.scalatest.FunSpec

class RailwaySpec extends FunSpec {
  describe("trajectory building") {
    it("builds an empty trajectory for an empty station list") {
      val rw = new Railway()
      val trj = rw.buildTrajectory(List())
      assert(trj.positions === Nil)
    }

    it("builds a single-station trajectory") {
      val rw = new Railway()
      val traj = rw.buildTrajectory(List('a'))
      assert(traj.positions === List(AtStation('a')))
    }

    it("includes the track info into the trajectory") {
      val rw = new Railway(('a', 'b') -> 1)
      val traj = rw.buildTrajectory(List('a', 'b'))
      assert(traj.positions === List(AtStation('a'), AtTrack('a', 'b'), AtStation('b')))
    }

    it("builds a trajectory for a multi-station route") {
      val rw = new Railway(('a', 'b') -> 1, ('b', 'c') -> 1)
      val traj = rw.buildTrajectory(List('a', 'b', 'c'))
      assert(traj.positions === List(AtStation('a'), AtTrack('a', 'b'), AtStation('b'), AtTrack('b', 'c'), AtStation('c')))
    }

    it("takes track length into account") {
      val rw = new Railway(('a', 'b') -> 2)
      val traj = rw.buildTrajectory(List('a', 'b'))
      assert(traj.positions === List(AtStation('a'), AtTrack('a', 'b'), AtTrack('a', 'b'), AtStation('b')))
    }

    it("understands the reverse route direction") {
      val rw = new Railway(('a', 'b') -> 2)
      val traj = rw.buildTrajectory(List('b', 'a'))
      assert(traj.positions === List(AtStation('b'), AtTrack('b', 'a'), AtTrack('b', 'a'), AtStation('a')))
    }

    it("assumes distance is 1 by default") {
      val rw = new Railway()
      intercept[MissingTrackError](rw.buildTrajectory(List('a', 'b')))
    }
  }
}
