import org.dandelion.trains.{Collision, Railway, Main}
import org.scalatest.FunSpec

class TrainsAcceptanceSpec extends FunSpec {

  import Main._

  def railway(route: ((Station, Station), Int)*): Railway[Main.Station] = Railway[Station](route: _*)

  def train(s: Station*): Train = List(s: _*)

  describe("trivial configurations") {
    it("reports no collision if there are no trains") {
      val rw = Railway[Station]()
      val trains = List()

      assertNoCollision(rw, trains)
    }

    it("reports no collision if there's only one train") {
      val rw = Railway[Station]()
      val trains = List(train('a'))

      assertNoCollision(rw, trains)
    }
  }


  describe("all tracks have the same length") {
    it("reports no collision when 2 trains start from different stations") {
      val rw = Railway[Station]()
      val trains = List(train('a'), train('b'))

      assertNoCollision(rw, trains)
    }

    it("reports collision when 2 trains start from the same station") {
      val rw = Railway[Station]()
      val trains = List(train('b'), train('a'), train('a'))
      assertCollide(rw, trains)
    }

    it("reports collision when 2 trains finish at the same station") {
      val rw = Railway(('a', 'b') -> 1, ('c', 'b') -> 1)
      val trains = List(train('a', 'b'), train('c', 'b'))

      assertCollide(rw, trains)
    }

    it("reports collision when one train finishes at the station and another passes it later") {
      val rw = Railway(('a', 'b') -> 1, ('c', 'b') -> 1, ('b', 'd') -> 1)
      val trains = List(train('a', 'b'), train('c', 'b', 'd'))

      assertCollide(rw, trains)
    }
  }


  describe("tracks have variable length") {
    it("takes track length into account") {
      val rw = Railway(('a', 'b') -> 1, ('b', 'c') -> 1, ('d', 'b') -> 2)
      val trains = List(train('a', 'b', 'c'), train('d', 'b'))

      assertNoCollision(rw, trains)
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

      assertNoCollision(rw, trains)
    }
  }

  def assertNoCollision(rw: Railway[Main.Station], trains: List[Train]) {
    assert(detectCollision(rw, trains) === None, "Collision is not expected")
  }

  def assertCollide(rw: Railway[Main.Station], trains: List[Main.Train]) {
    assert(detectCollision(rw, trains) === Some(Collision()), "Collision is expected")
  }
}
