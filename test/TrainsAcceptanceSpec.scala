import org.scalatest.FunSpec
import org.dandelion.trains._
import Main._
import scala.Some


class TrainsAcceptanceSpec extends FunSpec {

  describe("trivial configurations") {
    it("reports no collision if there are no trains") {
      val trains = List()

      assertNoCollision(trains)
    }

    it("reports no collision if there's only one train") {
      val rw = new Railway()
      val trains = List(rw.train(1, 'a'))

      assertNoCollision(trains)
    }
  }


  describe("all tracks have the same length") {
    it("reports no collision when 2 trains start from different stations") {
      val rw = new Railway()
      val trains = List(rw.train(1, 'a'), rw.train(2, 'b'))

      assertNoCollision(trains)
    }

    it("reports collision when 2 trains start from the same station") {
      val rw = new Railway()
      val trains = List(rw.train(1, 'b'), rw.train(2, 'a'), rw.train(3, 'a'))
      assertCollide(trains, (2, 3, AtStation('a')))
    }

    it("reports collision when 2 trains finish at the same station") {
      val rw = new Railway(('a', 'b') -> 1, ('c', 'b') -> 1)
      val trains = List(rw.train(1, 'a', 'b'), rw.train(2, 'c', 'b'))

      assertCollide(trains, (1, 2, AtStation('b')))
    }

    it("reports collision when one train finishes at the station and another passes it later") {
      val rw = new Railway(('a', 'b') -> 1, ('c', 'b') -> 1, ('b', 'd') -> 1)
      val trains = List(rw.train(1, 'a', 'b'), rw.train(2, 'c', 'b', 'd'))

      assertCollide(trains, (1, 2, AtStation('b')))
    }

    it("reports collision when trains meet at the track") {
      val rw = new Railway(('a', 'b') -> 1)
      val trains = List(rw.train(1, 'a', 'b'), rw.train(2, 'b', 'a'))

      assertCollide(trains, (1, 2, AtTrack('a', 'b')))
    }
  }


  describe("tracks have variable length") {
    it("takes track length into account") {
      val rw = new Railway(('a', 'b') -> 1, ('b', 'c') -> 1, ('d', 'b') -> 2)
      val trains = List(rw.train(1, 'a', 'b', 'c'), rw.train(2, 'd', 'b'))

      assertNoCollision(trains)
    }

    it("does not report collision if trains follow each other on the same track") {
      val rw = new Railway(
        ('a', 'c') -> 2,
        ('b', 'c') -> 1,
        ('c', 'd') -> 4,
        ('d', 'e') -> 1,
        ('d', 'f') -> 1)

      val trains = List(
        rw.train(1, 'a', 'c', 'd', 'e'),
        rw.train(2, 'b', 'c', 'd', 'f'))

      assertNoCollision(trains)
    }
  }

  def assertNoCollision(trains: List[Train]) {
    assert(detectCollision(trains) === None, "Collision is not expected")
  }

  def assertCollide(trains: List[Train], colliding: (Int, Int, Position)) {
    detectCollision(trains) match {
      case None => fail("collision is expected")
      case Some(Collision(t1, t2, pos)) => {
        if (!((t1.number, t2.number, pos) == colliding))
          fail("Collision is expected for trains " + colliding + ", but was for " + (t1.number, t2.number, pos))
      }
    }
  }
}
