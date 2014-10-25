package userWallet
import org.springframework.context.annotation.{Conditional,ConditionContext,Configuration}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.{RequestMapping,RequestMethod,RequestParam,RequestBody,RequestHeader}
import org.springframework.web.bind.annotation.{ResponseBody,ResponseStatus,PathVariable}
import org.springframework.web.bind.annotation.RestController
import collection.JavaConverters._
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.http.ResponseEntity
import java.util.Date
import org.springframework.http._
import scala.util.control.Exception._
import java.lang._
import java.util.Iterator
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import javax.validation.constraints.{Min, NotNull, Size}
import javax.validation._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.core.CacheControl
import javax.ws.rs.core.Response
import javax.ws.rs.core.Request
import javax.ws.rs.core.EntityTag
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.{RuntimeDelegate,FactoryFinder}
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import org.json.{JSONArray,JSONObject}
	
@Configuration
@EnableAutoConfiguration
@ComponentScan
@RestController
class WalletConfig {


   @Autowired
   private var repository:UserRepository = _;
   @Autowired
   private var idCardRepository:IdCardRepository = _;
   @Autowired
   private var uiRepository:UserIDCardRepository = _;
   
   
    @Autowired
   private var webLoginRepository:WebLoginRepository = _;
   @Autowired
   private var uwRepository:UserWebLoginRepository = _;
   
   
   
    @Autowired
   private var baRepository:BankAccountRepository = _;
   @Autowired
   private var ubRepository:UserBankAccountRepository = _;
   
   
  
  @RequestMapping(value = Array("/api/v1/users"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
   def UserConfig(@Valid @RequestBody(required = true) userObj : User) : User 
  = {
    
        var pastUserId: String = getNextUser("User")
        
        if(pastUserId == null)
        	userObj.setUserId()
        else
          userObj.id  = "u-" + ((pastUserId.replaceFirst("u-", "")).toInt + 1)
          userObj.setCreated_at()
          repository.save(userObj);
          return userObj
    
  }
  
  
  
  
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = Array("/api/v1/users/{user_id}"), method = Array(RequestMethod.GET))
  def getUser(@PathVariable user_id : String, @RequestHeader(value="Etag", required=true , defaultValue="") eTag: String) : Any = {
			 var userId: String = user_id
			
			var userObj:User = null
			try
			 {
		  		  userObj = repository.findUserById(userId)
		  		   // println(userObj.email);
		  		    //WalletConfig.userMap(userId) 
			 }
			 catch
			 {
			   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
			   
			 }
				 
		  		 if(userObj != null)
		  		 {
		  		   
		  		   
		  			 	val tag: String = eTag
		  			 	
		  			 		
						  //var userObj:User = check.asInstanceOf[User]
					 		  			 	
		  			 	  val cacheControl: CacheControl = new CacheControl()
						  cacheControl.setMaxAge(216)
						  var x: String = ""
						    
						  if(userObj.updated_at != null)
						    x = Integer.toString(userObj.updated_at.hashCode())
						  
			  			 	var etag : EntityTag = new EntityTag(x)
						    val httpResponseHeader: HttpHeaders = new HttpHeaders()
						    httpResponseHeader.setCacheControl(cacheControl.toString)
						    httpResponseHeader.add("Etag", etag.getValue)
						    
						 
						    if(etag.getValue.equalsIgnoreCase(tag)){
						      
						      return new ResponseEntity[String]( null, httpResponseHeader, HttpStatus.NOT_MODIFIED )
						    } 
						    else { 
						      return new ResponseEntity[User]( userObj, httpResponseHeader, HttpStatus.OK )
						    }
							  			 	
		  			 	
					      	
					   
		  		 }
		  		 //throw new WebApplicationException(400
		  		  return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
			 
			 
  }
  
  
  
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}"), method = Array(RequestMethod.PUT), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
   def updateUser(@Valid @RequestBody userObjNew : User,
 @PathVariable user_id : String) : Any = {
		   		var userId: String = user_id
			
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		 
		   		 
		  		 if(userObj != null)
		  		 {
			  		    
					    
							  //var userObj:User = check.asInstanceOf[User]
							  
							  
							  userObj.setEmail(userObjNew.email) 
							  userObj.setPassword(userObjNew.password ) 
							  userObj.setName(userObjNew.name ) 
							  userObj.setUpdated_at();
							  
							  
							  
							  repository.save(userObj)
							  
						      var listTest:Map[String,String] = Map()
						      
						      listTest += ("user_Id" -> (userObj.getUser_id().toString()))
						      listTest += ("email" -> userObj.getEmail())
						      listTest += ("password" -> userObj.getPassword())
						      listTest += ("created_at" -> userObj.getCreated_at().toString())
						      
						      return listTest.asJava
					    
		  		 }
		  	 return null
		      
      
  }
  
  
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def addIdcard(@Valid @RequestBody idCard : IDCard,
 @PathVariable user_id : String) : Any = {
      
				var userId: String = user_id
			
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		   var pastId: String = getNextUser("IDCard")
        
			        if(pastId == null)
			        	idCard.setCard_id();
			        else
			          idCard.id  = "c-" + ((pastId.replaceFirst("c-", "")).toInt + 1)
		  			 	
						
						
						idCardRepository.save(idCard);
						uiRepository.save(new UserIDCard(idCard.id,userId))
						
						
			  			return idCard;	  
			      }
		  	 return null
		
  }
  
 
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getAllIdcards(@PathVariable user_id : String) : Any = {
      val li	 = new ArrayList[Any]()

		
			var userId: String = user_id
			
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		  
		  		//  WalletConfig.listAllIdCard = Map()
		  		   
		  			 	var idCard : String = null
						var temp: IDCard = null
		  			 //var idCardMapJava : java.util.Map[String,Any] = WalletConfig.idCardMap.asJava
		  			 	
		  			 	var idCardMapJava : java.util.List[UserIDCard] = uiRepository.findUserIDCardByUserId(userId)
		  			 	 
		  			 	
		  			 	var iterator  = idCardMapJava.iterator();
				        while (iterator.hasNext()) {
				             var values =  iterator.next();
				             idCard = values.id 
				             
				            temp = idCardRepository.findIDCardById(idCard)
				               
				               
				            	 li.add(temp)
			            } 
					      return li
		  		 }
		  	 return null
		
  }
  
 
 
    @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards/{card_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteIdCard(@PathVariable user_id : String,@PathVariable card_id : String) : Any = {
     

		
	
			 var userId: String = user_id
			 var cardId: String = card_id
	
		  		
					
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		  
		  		     var checkIDCard =idCardRepository.findIDCardById(cardId)
		  		
			  		 if(checkIDCard != null)
			  		 {
			  			 //println(uiRepository.findUserIdById(cardId))
		  		   
			  			 	var temp: UserIDCard = uiRepository.findUserIDCardById(cardId)
				  			if( temp.userId  == userId)
			  			 	{
			  		   
			  		   
				  			 	var idCard : IDCard = checkIDCard.asInstanceOf[IDCard]
				  			 	
				  			 	
				  			 	
	 							idCardRepository.delete(idCard)
	 							
				  			 	uiRepository.delete(temp)
				  			 	
				  			 	return new ResponseEntity[String]( "ID Card Deleted successfully!", null, HttpStatus.NO_CONTENT )
				  			 	
			  			 	}
				  			else
				  			  return new ResponseEntity[String]( "No matching content!", null, HttpStatus.NOT_FOUND )
			  		 }
		  		 }
		  	return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
		     
      
  }
  
 @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def addLoginId(@Valid @RequestBody loginObj : WebLogin,
 @PathVariable user_id : String) : Any = {
      
		  		var userId: String = user_id
			
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)		  		 
		  		 {
		  			 	
							 
							  var pastId: String = getNextUser("WebLogin")
        
						        if(pastId == null)
						        	loginObj.setLogin_id();
						        else
						          loginObj.id  = "l-" + ((pastId.replaceFirst("l-", "")).toInt + 1)
		  			 	
						
							  
							  webLoginRepository.save(loginObj);
							  uwRepository.save(new UserWebLogin(loginObj.id,userId))
						
							  
							 
			  			 	  
			  			 	  return loginObj;
		  		 }
		  	 return null
		      
      
  }
   
  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getAllLoginIds(@PathVariable user_id : String) : Any = {
		        val li	 = new ArrayList[Any]()
		
				var userId: String = user_id	
					
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		  
		  		
		  			 	
		  			 	
		  			 	 var webLogin : String = null
						var temp: WebLogin = null
		  			 
		  			 	
		  			 	var loginIdMapJava : java.util.List[UserWebLogin] = uwRepository.findUserWebLoginByUserId(userId)
		  			 	 
		  			 	
		  			 	
		  			 	var iterator  = loginIdMapJava.iterator();
				        while (iterator.hasNext()) {
				             var values =  iterator.next();
				             webLogin = values.id
				             
				           temp = webLoginRepository.findIDCardById(webLogin)
				             li.add(temp)
				               
				        } 
		  			 	return li
		  		 }
		  	 return null
		
		      
      
  }
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins/{login_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteLogin(@PathVariable user_id : String,@PathVariable login_id : String) : Any = {
     

		
	
			 var userId: String = user_id
			 var loginId: String = login_id
	
		  		 var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		  
		  		     var checkLoginId = webLoginRepository.findIDCardById(loginId) 
		  		
			  		 if(checkLoginId != null)
			  		 {
		  		   
			  			 	var temp: UserWebLogin = uwRepository.findUserWebLoginById(loginId)
				  			if( temp.userId  == userId)
			  			 	{
			  			 	
				  			
			  			 			var loginObj : WebLogin = checkLoginId.asInstanceOf[WebLogin]
					  			 	
					  			 	webLoginRepository.delete(loginObj)
					  			 	uwRepository.delete(temp)
					  			 	
		 							
					  			 	return new ResponseEntity[String]( "Web login Id deleted successfully!", null, HttpStatus.NO_CONTENT )
			  			 	}
			  			 	else
			  			 	  return new ResponseEntity[String]( "Not matching content!", null, HttpStatus.NOT_FOUND )
			  		 }
		  		 }
		  	 return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
		      
      
  }
  
 @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
 @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def addBaId(@Valid @RequestBody(required = true) bankObj :  BankAccount,
      @PathVariable user_id : String ) : Any = {

		
   
				 
				var userId: String = user_id	
					
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
				 {
						  
		  		   ////////Routing number validation/////////////
		  		   
		  		   
		  		   val jsonResponse = Unirest.get("http://www.routingnumbers.info/api/data.json")
				      .field("rn",bankObj.routing_number).asJson()
				      
				      
				      if(jsonResponse.getCode() == 200)
				      {
		  		   
		  		   ///////////////////
				    	  	 var body:JsonNode = jsonResponse.getBody()
				    	  	 
				    	  	 
				    	  	 var array:JSONArray = body.getArray()
				    	  	 
				    	  	 
				    	  	 
				    	  	 var customerName:JSONObject = array.getJSONObject(0)
				    	  	 
				    	  	
							  var pastId: String = getNextUser("BankAccount")
        
						        if(pastId == null)
						        	bankObj.setBa_id();
						        else
						          bankObj.id  = "b-" + ((pastId.replaceFirst("b-", "")).toInt + 1)
		  			 	
						       if(bankObj.account_name  == "")   
						    	   	bankObj.account_name  = customerName.getString("customer_name")
						  
									   
							  baRepository.save(bankObj);
							  ubRepository.save(new UserBankAccount(bankObj.id,userId))
						
							  
						  
						  Unirest.shutdown()	  
		  			 	  
		  			 	  return bankObj;
				      }
				      else
				      {
				        Unirest.shutdown()
				      
				        return new ResponseEntity[String]( "Invalid routing number!", null, HttpStatus.NOT_FOUND )
				      }
				 }
				 return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				     
		    
  } 	
  
 
 
 @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getAllBaIds(@PathVariable user_id : String) : Any = {
			    val li	 = new ArrayList[Any]()
				var userId: String = user_id	
					
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		  
		  		  
		  		   
		  			 	var bankAccount : String = null
						var temp: BankAccount = null
		  			 
		  			 	
		  			 	var bankAccountMapJava : java.util.List[UserBankAccount] = ubRepository.findUserBankAccountByUserId(userId)
		  			 	 
		  			 	
		  			 	
		  			 	var iterator  = bankAccountMapJava.iterator();
				        while (iterator.hasNext()) {
				             var values =  iterator.next();
				             bankAccount = values.id
				             
				           temp = baRepository.findBankAccountById(bankAccount)
				             li.add(temp)
				             
				          
				        } 
		  			 	return li
		  		 }
		  	 return null
		
  }
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteBA(@PathVariable user_id : String,@PathVariable ba_id : String) : Any = {
     
		  		var baId: String = ba_id
		
	
			    var userId: String = user_id	
					
				var userObj:User = null
				try
				 {
			  		  userObj = repository.findUserById(userId)
			  		  
				 }
				 catch
				 {
				   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
				 }	 
		   		  		
		  		 if(userObj != null)
		  		 {
		  		  
		  		     var checkBaId = baRepository.findBankAccountById(baId) 
		  		
			  		 if(checkBaId != null)
			  		 {
		  		   
		  		   
		  		   var temp: UserBankAccount = ubRepository.findUserBankAccountById(baId)
				  			if( temp.userId  == userId)
			  			 	{
				  			
			  		   
				  			 	var baObj : BankAccount = checkBaId.asInstanceOf[BankAccount]
				  			 	
				  			 	baRepository.delete(baObj)
				  			 	ubRepository.delete(temp)
				  			 	return new ResponseEntity[String]( "Bank Account Id deleted successfully!", null, HttpStatus.NO_CONTENT )
				  			 }
				  			else
				  				return new ResponseEntity[String]( "No matching content!", null, HttpStatus.NOT_FOUND )
			  		 }
		  		 }
		  	 return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
				   
		
  }
  
  
  
   
  def getNextUser(temp:String) : String = {
			 
				
		  if(temp == "User")
		  {
    
    
		  	var userObj:java.util.List[User]  = null;
			
			try
			 {
		  		  userObj = repository.findAll();
		  		   if(userObj != null)
		  		 {
		  		   
		  			 	var size: Int =userObj.size()-1 
		  			 	
		  			 	
		  			 	return userObj.get(size).id 
					      	
					   
		  		 }
		  		   else
		  		 {
		  		   
		  			 	return null
		  		 }
			 }
			 catch
			 {
			   
			    case e : Exception => return null
			 }
				 
		  		 
		  }
		  if(temp == "IDCard")
		    {
		    		var obj:java.util.List[IDCard]  = null;
			
					try
					 {
				  		  obj = idCardRepository.findAll();
				  		   if(obj != null)
				  		 {
				  		   
				  			 	var size: Int =obj.size()-1 
				  			 	
				  			 	
				  			 	return obj.get(size).id 
							      	
							   
				  		 }
				  		   else
				  		 {
				  		   
				  			 	return null
				  		 }
					 }
					 catch
					 {
					   
					    case e : Exception => return null
					 }
				
		    	
		    }
		  
		    if(temp == "BankAccount")
		    {
		    		var obj:java.util.List[BankAccount]  = null;
			
					try
					 {
				  		  obj = baRepository.findAll();
				  		   if(obj != null)
				  		 {
				  		   
				  			 	var size: Int =obj.size()-1 
				  			 	
				  			 	
				  			 	return obj.get(size).id 
							      	
							   
				  		 }
				  		   else
				  		 {
				  		   
				  			 	return null
				  		 }
					 }
					 catch
					 {
					   
					    case e : Exception => return null
					 }
				
		    	
		    }
		   if(temp == "WebLogin")
		    {
		    		var obj:java.util.List[WebLogin]  = null;
			
					try
					 {
				  		  obj = webLoginRepository.findAll();
				  		   if(obj != null)
				  		 {
				  		   
				  			 	var size: Int =obj.size()-1 
				  			 	
				  			 	
				  			 	return obj.get(size).id 
							      	
							   
				  		 }
				  		   else
				  		 {
				  		   
				  			 	return null
				  		 }
					 }
					 catch
					 {
					   
					    case e : Exception => return null
					 }
				
		    	
		    }
		   return null;
		   
  }
  
   
}


