package userWallet

import org.springframework.boot.SpringApplication

object MyWallet {

	def main(args: Array[String]) {
	 
	   SpringApplication.run(classOf[WalletConfig]);
	}
}