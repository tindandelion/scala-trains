import org.scalatest.FunSpec

class Train(val station: String)

object Trains {
  def hasCrash(trains: Array[Train]) =
    if (trains.size > 1) trains(0).station == trains(1).station
    else false
}

class TrainsAcceptanceSpec extends FunSpec {

  import Trains._

  describe("initial configurations") {
    it("reports no crash if there are no trains") {
      assert(!hasCrash(Array()))
    }

    it("reports no crash if there's only one train") {
      val trains = Array(new Train("a"))
      assert(!hasCrash(trains))
    }

    it("reports no crash when 2 trains start from different stations") {
      val trains = Array(new Train("a"), new Train("b"))
      assert(!hasCrash(trains))
    }

    it("reports crash when 2 trains start from the same station") {
      val trains = Array(new Train("b"), new Train("a"), new Train("a"))
      assert(hasCrash(trains), "crash is expected")
    }
  }
}
