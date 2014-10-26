package userWallet

import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import org.springframework.context.support.GenericXmlApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.context.ApplicationContext;

import com.mongodb.MongoClient
//remove if not needed
import scala.collection.JavaConversions._
/*
@Configuration
class SpringMongoConfigTest {

  @Bean
  def mongoDbFactory(): MongoDbFactory = {
    new SimpleMongoDbFactory(new MongoClient(), "yourdb")
  }

  @Bean
  def mongoTemplate(): MongoTemplate = {
    val mongoTemplate = new MongoTemplate(mongoDbFactory())
    mongoTemplate
  }
}
*/


object MyWallet {

  /*  val ctx = new GenericXmlApplicationContext("springConfig.xml");
      val mongoOperation = ctx.getBean("mongoTemplate").asInstanceOf[MongoOperations]
    */
	def main(args: Array[String]) {
	  /* var x = new SpringMongoConfigTest()
	   x.mongoDbFactory()
	   */
	  
	// println("test== " + mongoOperation)
    
    
    
    
    
	   SpringApplication.run(classOf[WalletConfig]);
	}
}