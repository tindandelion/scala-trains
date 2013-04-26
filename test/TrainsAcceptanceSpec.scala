import org.dandelion.trains.{Main, Train}
import org.scalatest.FunSpec

class TrainsAcceptanceSpec extends FunSpec {

  import Main._

  describe("trivial configurations") {
    it("reports no collision if there are no trains") {
      assert(!hasCollisions(List()))
    }

    it("reports no collision if there's only one train") {
      val trains = List(trainStartingAt("a"))
      assert(!hasCollisions(trains))
    }
  }

  describe("all tracks have the same length") {
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

    it("reports collision when one train finishes at the station and another passes it later") {
      val trains = List(
        new Train(List('a', 'b')),
        new Train(List('c', 'b', 'd')))

      assert(hasCollisions(trains), "collision is expected")
    }
  }

  describe("tracks have variable length") {
    it("takes track length into account") {
      val tracks = Map[(Any, Any), Int](
        ('a', 'b') -> 1,
        ('b', 'c') -> 1,
        ('d', 'b') -> 2)

      val trains = List(
        new Train(List('a', 'b', 'c'), tracks),
        new Train(List('d', 'b'), tracks))

      assert(!hasCollisions(trains), "collision is not expected")
    }
  }

  def trainStartingAt(station: String): Train = new Train(List(station))
}
