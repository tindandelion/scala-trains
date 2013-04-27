import org.dandelion.trains.{Railway, Main}
import org.scalatest.FunSpec

class TrainsAcceptanceSpec extends FunSpec {

  import Main._

  def railway(route: ((Station, Station), Int)*): Railway[Main.Station] = Railway[Station](route: _*)

  def train(s: Station*): Train = List(s: _*)

  describe("trivial configurations") {
    it("reports no collision if there are no trains") {
      val rw = Railway[Station]()
      val trains = List()
      assert(!collide(rw, trains))
    }

    it("reports no collision if there's only one train") {
      val rw = Railway[Station]()
      val trains = List(train('a'))
      assert(!collide(rw, trains))
    }
  }

  describe("all tracks have the same length") {
    it("reports no collision when 2 trains start from different stations") {
      val rw = Railway[Station]()
      val trains = List(train('a'), train('b'))
      assert(!collide(rw, trains))
    }

    it("reports collision when 2 trains start from the same station") {
      val rw = Railway[Station]()
      val trains = List(train('b'), train('a'), train('a'))
      assert(collide(rw, trains))
    }

    it("reports collision when 2 trains finish at the same station") {
      val rw = Railway(('a', 'b') -> 1, ('c', 'b') -> 1)
      val trains = List(train('a', 'b'), train('c', 'b'))

      assert(collide(rw, trains))
    }

    it("reports collision when one train finishes at the station and another passes it later") {
      val rw = Railway(('a', 'b') -> 1, ('c', 'b') -> 1, ('b', 'd') -> 1)
      val trains = List(train('a', 'b'), train('c', 'b', 'd'))

      assert(collide(rw, trains))
    }
  }

  describe("tracks have variable length") {
    it("takes track length into account") {
      val rw = Railway(('a', 'b') -> 1, ('b', 'c') -> 1, ('d', 'b') -> 2)
      val trains = List(train('a', 'b', 'c'), train('d', 'b'))

      assert(!collide(rw, trains))
    }

    it("does not report collision if trains follow each other on the same track") {
      val rw = Railway(
        ('a', 'c') -> 2,
        ('b', 'c') -> 1,
        ('c', 'd') -> 4,
        ('d', 'e') -> 1,
        ('d', 'f') -> 1)

      val trains = List(
        train('a', 'c', 'd', 'e'),
        train('b', 'c', 'd', 'f'))

      assert(!collide(rw, trains))
    }
  }
}
