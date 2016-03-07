import org.scalatest._
import org.scalatest.{FunSpecLike, Matchers}
import scala.concurrent._
import scala.concurrent.duration._
import com.akkademy.SClient
import com.akkademy.messages.KeyNotFoundException


class SClientIntegrationSpec extends FunSpecLike with Matchers {

    var client = new SClient("127.0.0.1:2552")

    describe("akkademyDb Scala Client") {
        describe("set method") {
            it("should set a value") {
                client.set("123", new Integer(123))
                val futureResult = client.get("123")
                val result = Await.result(futureResult, 10 seconds)
                result should equal(123)
            }
        }

        describe("setIfNotExists method") {
            it("should not set a value if it already exists") {
                client.setIfNotExists("key", new Integer(123))
                client.setIfNotExists("key", new Integer(555))
                val futureResult = client.get("key")
                val result = Await.result(futureResult, 10 seconds)
                result should equal(123)
            }

            it("should set a value if not exists") {
                //trick untill the server is mocked
                client.setIfNotExists("newKey", new Integer(555))
                val futureResult = client.get("newKey")
                val result = Await.result(futureResult, 10 seconds)
                result should equal(555)
            }
        }

        describe("delete method") {
            it("should delete a key if it exists") {
                client.set("123", new Integer(123))
                val futureResult = client.delete("123")
                val result = Await.result(futureResult, 10 seconds)
                result shouldBe a [java.lang.Boolean]
                result should equal(true)
            }

            it("should fail with KeyNotFoundException when deleting a key that does not exist") {
                val futureResult = client.delete("unknown")
                val thrown = intercept[KeyNotFoundException] {
                    Await.result(futureResult, 10 seconds)
                }
                thrown.key should equal("unknown")
            }
        }
    }
}