package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.model.RoleModel;
import com.example.model.UserModel;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

//import com.example.configuration.ApplicationArguments;
//import com.example.configuration.ApplicationRunner;
//import com.example.demo.configuration.Autowired;
//import com.example.demo.configuration.Component;
//import com.example.demo.configuration.RoleModel;
//import com.example.demo.configuration.RoleRepository;
//import com.example.demo.configuration.UserModel;
//import com.example.demo.configuration.UserRepository;




@Component
public class DataLoader implements ApplicationRunner{
		
		 @Autowired
		 private RoleRepository roleRepository;
		
		 @Autowired
		 private UserRepository userRepository;
		
		
		
		 public void run(ApplicationArguments args) throws InterruptedException {
//		
//			roleRepository.save(new RoleModel("BIDDER"));
//			roleRepository.save(new RoleModel ("APPROVER"));
//			
//			userRepository.save(new UserModel(1, "bidderl","companyOne", "bidder123$", "bidderemail@gmail.com", new RoleModel (1)));
//			userRepository.save(new UserModel (2, "bidder2", "company Two", "bidder789$", "bidderemail2@gmail.com", new RoleModel(1)));
//			userRepository.save(new UserModel (3, "approver", "defaultCompany", "approver123$", "approveremail@gmail.com" ,new RoleModel (2)));
//		 

			//Insert roles first and save references
			//RoleModel bidderRole = roleRepository.save(new RoleModel("BIDDER"));
			//RoleModel approverRole = roleRepository.save(new RoleModel("APPROVER"));
			
			
			
//			RoleModel bidderRole = roleRepository.findByRolename("BIDDER")
//		            .orElseGet(() -> roleRepository.save(new RoleModel("BIDDER")));
//
//		    RoleModel approverRole = roleRepository.findByRolename("APPROVER")
//		            .orElseGet(() -> roleRepository.save(new RoleModel("APPROVER")));
//			
//			// Use the saved roles instead of creating new ones with just ID
//			userRepository.save(new UserModel(1, "bidder1", "companyOne", "bidder123$", "bidderemail@gmail.com", bidderRole));
//			userRepository.save(new UserModel(2, "bidder2", "companyTwo", "bidder789$", "bidderemail2@gmail.com", bidderRole));
//			userRepository.save(new UserModel(3, "approver", "defaultCompany", "approver123$", "approveremail@gmail.com", approverRole));



}
//
}