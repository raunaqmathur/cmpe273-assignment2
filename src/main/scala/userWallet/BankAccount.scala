package userWallet

//import org.glassfish.jersey
//import javax.validation.constraints.Pattern
import org.hibernate.validator.constraints.NotEmpty
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude._
import org.springframework.data.annotation.Id;

import scala.collection.JavaConversions._

object BankAccount{
    private var bankIDCounter : Int = 123455
    private def inc = {bankIDCounter += 1; bankIDCounter}
}

class BankAccount {
  
		@Id
		 var  id :String  = ""
		
		@JsonInclude(value=Include.NON_EMPTY)
		var account_name  : String = ""
		  
		  
		  @NotEmpty(message =  "{Invalid Bank Routing Number}")
		var routing_number  : String = ""
		
		   @NotEmpty(message =  "{Invalid Bank Account Number}")
		var account_number  :String = ""
		
		
		
		
		def getBa_id() : String = id 
		
		def getAccount_name() : String = account_name
		
		def getRouting_number(): String = routing_number
		
		def getAccount_number(): String = account_number
	
		
		
		def setAccount_name(s:String)  = {account_name=s}
		
		def setRouting_number(s: String) = {routing_number=s}
		
		def setAccount_number(s: String) = {account_number=s}
		
		def setBa_id()   = {
		  id = "b-" + BankAccount.inc
		}
		 
		

}