package userWallet

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import scala.collection.JavaConversions._
import org.springframework.data.repository.Repository


import org.springframework.data.annotation.Id;

import scala.collection.JavaConversions._

class UserIDCard
{
  
  var id: String = "";
  var userId: String = "";
  
  def this(id: String, userId: String) {
    this()
    this.id = id
    this.userId = userId
  }
  
}

//@Repository
trait UserIDCardRepository extends MongoRepository[UserIDCard, String] {

  
    def findUserIDCardById(idCard : String) : UserIDCard
    def findUserIDCardByUserId(userId : String) : List[UserIDCard]
  
}



