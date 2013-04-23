import org.dandelion.trains.{Main, Train}
import org.scalatest.FunSpec

class TrainsAcceptanceSpec extends FunSpec {

  import Main._

  describe("initial configurations") {
    it("reports no collision if there are no trains") {
      assert(!hasCollisions(List()))
    }

    it("reports no collision if there's only one train") {
      val trains = List(trainStartingAt("a"))
      assert(!hasCollisions(trains))
    }

    it("reports no collision when 2 trains start from different stations") {
      val trains = List(trainStartingAt("a"), trainStartingAt("b"))
      assert(!hasCollisions(trains))
    }

    it("reports collision when 2 trains start from the same station") {
      val trains = List(trainStartingAt("b"), trainStartingAt("a"), trainStartingAt("a"))
      assert(hasCollisions(trains), "crash is expected")
    }

    it("reports collision when 2 trains finish at the same station") {
      val trains = List(
        new Train(List("a", "b")),
        new Train(List("c", "b")))

      assert(hasCollisions(trains), "collision is expected")
    }
  }

  def trainStartingAt(station: String): Train = new Train(List(station))
}
