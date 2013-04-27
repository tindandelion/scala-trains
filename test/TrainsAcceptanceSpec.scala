import org.dandelion.trains.{Train, Collision, Railway, Main}
import org.scalatest.FunSpec
import Main._
import org.dandelion.trains.Types._


class TrainsAcceptanceSpec extends FunSpec {

  def railway(route: ((Station, Station), Int)*): Railway = Railway(route: _*)

  def train(number: Int, stations: Station*) = Train(number, stations.toList)

  describe("trivial configurations") {
    it("reports no collision if there are no trains") {
      val rw = Railway()
      val trains = List()

      assertNoCollision(rw, trains)
    }

    it("reports no collision if there's only one train") {
      val rw = Railway()
      val trains = List(train(1, 'a'))

      assertNoCollision(rw, trains)
    }
  }


  describe("all tracks have the same length") {
    it("reports no collision when 2 trains start from different stations") {
      val rw = Railway()
      val trains = List(train(1, 'a'), train(2, 'b'))

      assertNoCollision(rw, trains)
    }

    it("reports collision when 2 trains start from the same station") {
      val rw = Railway()
      val trains = List(train(1, 'b'), train(2, 'a'), train(3, 'a'))
      assertCollide(rw, trains, (2, 3))
    }

    it("reports collision when 2 trains finish at the same station") {
      val rw = Railway(('a', 'b') -> 1, ('c', 'b') -> 1)
      val trains = List(train(1, 'a', 'b'), train(2, 'c', 'b'))

      assertCollide(rw, trains, (1, 2))
    }

    it("reports collision when one train finishes at the station and another passes it later") {
      val rw = Railway(('a', 'b') -> 1, ('c', 'b') -> 1, ('b', 'd') -> 1)
      val trains = List(train(1, 'a', 'b'), train(2, 'c', 'b', 'd'))

      assertCollide(rw, trains, (1, 2))
    }
  }


  describe("tracks have variable length") {
    it("takes track length into account") {
      val rw = Railway(('a', 'b') -> 1, ('b', 'c') -> 1, ('d', 'b') -> 2)
      val trains = List(train(1, 'a', 'b', 'c'), train(2, 'd', 'b'))

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
        train(1, 'a', 'c', 'd', 'e'),
        train(2, 'b', 'c', 'd', 'f'))

      assertNoCollision(rw, trains)
    }
  }

  def assertNoCollision(rw: Railway, trains: List[Train]) {
    assert(detectCollision(rw, trains) === None, "Collision is not expected")
  }

  def assertCollide(rw: Railway, trains: List[Train], colliding: (Int, Int)) {
    detectCollision(rw, trains) match {
      case None => fail("collision is expected")
      case Some(Collision(t1, t2)) => {
        if (!((t1, t2) == colliding))
          fail("Collision is expected for trains " + colliding + ", but was for " + (t1, t2))
      }
    }
  }
}
