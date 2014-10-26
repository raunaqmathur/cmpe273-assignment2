package userWallet

import java.util.Calendar
import org.hibernate.validator.constraints.{NotEmpty,Email}
import com.fasterxml.jackson.annotation.JsonFormat
import org.codehaus.jackson.annotate.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude._
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import scala.collection.JavaConversions._


object User{
    private var userIdCounter : Int = 123455
    private def inc = {userIdCounter += 1; userIdCounter}
}

//@Document(collection = "User")
class User {
		
  
  
  
		@Id
		 var  id :String  = "";
		 
	    @NotEmpty(message =  "{Invalid Email address}") @Email
		var email : String = "";
	    
		@NotEmpty(message =  "{Invalid password}")
		var password : String = "";
		
		@JsonInclude(value=Include.NON_EMPTY)
		var name : String = "";
		
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		var created_at :java.util.Date = _
		
		@JsonIgnore
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		  var updated_at : java.util.Date= _
		
		
		
		def getUser_id() : String = id 
		
		def getEmail() : String = email
		
		def getPassword(): String = password
		
		def getName() : String = name
		
		def getCreated_at(): java.util.Date = created_at
		
		//def getUpdated_at() : java.util.Date = updated_at
		
		
		
		def setEmail(s : String) = { email = s}
		
		def setPassword(s: String) = {password = s}
		
		def setName(s : String) = {name =s}
		
		
		def setUpdated_at()   = {
		  updated_at = java.util.Calendar.getInstance.getTime;
		}
		
		
		
		def setCreated_at()   = {
		  created_at = java.util.Calendar.getInstance.getTime;
		}
		
		
		
		def setUserId()   = {
		  id = "u-" + User.inc
		}
		
		
		
		override def toString() : String = {
        return String.format(
                "User[user_id=%s, email='%s', password='%s', name=;'%s', created_at='%s']",
                id, email, password,name,created_at);
    }
		
	def this(email: String, name: String, password : String) {
    this()
    this.email = email
    this.name = name
    this.password = password
    this.created_at = java.util.Calendar.getInstance.getTime
  }

		
		
}
