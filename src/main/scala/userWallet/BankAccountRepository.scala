package userWallet

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import scala.collection.JavaConversions._
import org.springframework.data.repository.Repository

//@Repository



trait BankAccountRepository extends MongoRepository[BankAccount, String] {

  
    def findBankAccountById(bankAccountId : String) : BankAccount;
  
}



