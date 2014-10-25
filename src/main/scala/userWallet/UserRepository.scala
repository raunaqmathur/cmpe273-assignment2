package userWallet

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import scala.collection.JavaConversions._
import org.springframework.data.repository.Repository

//@Repository
trait UserRepository extends MongoRepository[User, String] {

  def findByEmail(email : String) : User;
    def findByName(name: String):List[User] ;

    def findUserById(userId : String) : User;
  
}



