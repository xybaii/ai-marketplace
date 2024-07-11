package aigc.backend;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import aigc.backend.repositories.CartRepository;
import aigc.backend.services.MailSenderService;






@SpringBootApplication
public class BackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	
	@Autowired
	CartRepository cartRepository;

	@Autowired 
	MailSenderService senderService;
	@Override
	public void run(String... args) throws Exception {
		// System.out.println(cartRepository.getCartItems("1") + "carts");
		
		// senderService.sendNewMail("xpeh001@e.ntu.edu.sg", "testing123", "this is sent from springboot");
			

	}


}