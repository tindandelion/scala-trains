import org.dandelion.trains.Train
import org.scalatest.FunSpec

class TrainSpec extends FunSpec {
  describe("collision detection") {
    it("collides at the final station even if arrives later") {
      val me = new Train(List('a', 'b', 'c'))
      val other = new Train(List('d', 'c'))

      assert(me.collidesWith(other), "collision is expected at the station 'c'")
    }
  }
}
