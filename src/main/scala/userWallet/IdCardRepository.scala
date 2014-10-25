package userWallet

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import scala.collection.JavaConversions._
import org.springframework.data.repository.Repository

//@Repository



trait IdCardRepository extends MongoRepository[IDCard, String] {

  
    def findIDCardById(idCard : String) : IDCard;
  
}



