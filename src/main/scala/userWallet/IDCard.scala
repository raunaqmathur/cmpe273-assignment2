package userWallet

import java.util.Calendar
import org.hibernate.validator.constraints.NotEmpty
import com.fasterxml.jackson.annotation.JsonFormat
import org.codehaus.jackson.annotate.JsonIgnore
import java.text._

import org.springframework.data.annotation.Id;

import scala.collection.JavaConversions._

object IDCard{
    private var IDCardCounter : Int = 123455
    private def inc = {IDCardCounter += 1; IDCardCounter}
}

class IDCard {
		@Id 
		var  id :String  = ""
		   
		@NotEmpty(message =  "{Invalid card name}")  
		var card_name : String = ""
		  
		@NotEmpty(message =  "{Invalid card number}")
		var card_number : String = ""
		
		@JsonIgnore  
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")  
		private var expiration_date :java.util.Date = _
		
		
		def getCard_id() : String = id 
		
		def getCard_name() : String = card_name
		
		def getCard_number(): String = card_number
		
		def getExpiration_date(): java.util.Date = expiration_date
	
		
		def setCard_id()   = {
		  id = "c-" + IDCard.inc
		}
		 
		 
		 def setCard_name(s: String) = {card_name=s}
		
		def setCard_number(s: String) = {card_number=s}
		
		def setExpiration_date(s:String ) = { 
		  var formatter : DateFormat = new SimpleDateFormat("MM-dd-yyyy");
		  var date:java.util.Date = formatter.parse(s);
		  expiration_date = date;
		  }
	
		
		override def toString() : String = {
        return String.format(
                "IDCard[user_id=%s, card_name='%s', card_number='%s', expiration_date=;'%s']",
                id, card_name, card_number,expiration_date);
		
}

}
