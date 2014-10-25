package userWallet

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import scala.collection.JavaConversions._
import org.springframework.data.repository.Repository


import org.springframework.data.annotation.Id;

import scala.collection.JavaConversions._

class UserBankAccount
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
trait UserBankAccountRepository extends MongoRepository[UserBankAccount, String] {

  
    def findUserBankAccountById(bankAccountId : String) : UserBankAccount
    def findUserBankAccountByUserId(userId : String) : List[UserBankAccount]
  
}



