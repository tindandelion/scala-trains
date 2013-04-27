import org.dandelion.trains._
import org.dandelion.trains.Collision
import org.dandelion.trains.Train
import org.scalatest.FunSpec
import Main._
import scala.Some


class TrainsAcceptanceSpec extends FunSpec {
  def railway(route: ((Char, Char), Int)*): Railway[Station] = {
    val stations = route.toList.map(v => ((Station(v._1._1), Station(v._1._2)), v._2))
    Railway(stations: _*)
  }

  def train(number: Int, stations: Char*) = Train(number, stations.toList.map(Station))

  describe("trivial configurations") {
    it("reports no collision if there are no trains") {
      val rw = railway()
      val trains = List()

      assertNoCollision(rw, trains)
    }

    it("reports no collision if there's only one train") {
      val rw = railway()
      val trains = List(train(1, 'a'))

      assertNoCollision(rw, trains)
    }
  }


  describe("all tracks have the same length") {
    it("reports no collision when 2 trains start from different stations") {
      val rw = railway()
      val trains = List(train(1, 'a'), train(2, 'b'))

      assertNoCollision(rw, trains)
    }

    it("reports collision when 2 trains start from the same station") {
      val rw = railway()
      val trains = List(train(1, 'b'), train(2, 'a'), train(3, 'a'))
      assertCollide(rw, trains)
    }

    it("reports collision when 2 trains finish at the same station") {
      val rw = railway(('a', 'b') -> 1, ('c', 'b') -> 1)
      val trains = List(train(1, 'a', 'b'), train(2, 'c', 'b'))

      assertCollide(rw, trains)
    }

    it("reports collision when one train finishes at the station and another passes it later") {
      val rw = railway(('a', 'b') -> 1, ('c', 'b') -> 1, ('b', 'd') -> 1)
      val trains = List(train(1, 'a', 'b'), train(2, 'c', 'b', 'd'))

      assertCollide(rw, trains)
    }
  }


  describe("tracks have variable length") {
    it("takes track length into account") {
      val rw = railway(('a', 'b') -> 1, ('b', 'c') -> 1, ('d', 'b') -> 2)
      val trains = List(train(1, 'a', 'b', 'c'), train(2, 'd', 'b'))

      assertNoCollision(rw, trains)
    }

    it("does not report collision if trains follow each other on the same track") {
      val rw = railway(
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

  def assertNoCollision(rw: Railway[Station], trains: List[Train]) {
    assert(detectCollision(rw, trains) === None, "Collision is not expected")
  }

  def assertCollide(rw: Railway[Station], trains: List[Train]) {
    assert(detectCollision(rw, trains) === Some(Collision()), "Collision is expected")
  }
}
