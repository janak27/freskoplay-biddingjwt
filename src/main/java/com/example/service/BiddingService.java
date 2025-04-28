package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.model.BiddingModel;
import com.example.model.UserModel;
import com.example.repository.BiddingRepository;

//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.dao. DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http. ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails. UserDetails;
//import org.springframework.stereotype.Service;
//import java.text.DateFormat:
//import java.text. SimpleDateFormat;
//import java.util.*;

@Service
public class BiddingService {


	/*Implement the business logic for the BiddingService operations in this class 219 22
	Make sure to add required annotations */

	@Autowired
	private BiddingRepository biddingRepository;

	@Autowired
	private UserService userService;
	
	
	
	public UserDetails getUser() {
		Object user =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof UserDetails) {
			return (UserDetails)user;
		}
		return null;
	}
	
	public String getTime() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	
	//to add the Bidding using BiddingModel object
	//created->201
	//badRequest->400
    public ResponseEntity<Object> postBidding (BiddingModel biddingModel) { 
    
    	try {
    		UserDetails userDetails =  getUser();
    		if(userDetails == null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		UserModel user =  userService.getUserByUsername(userDetails.getUsername());
    		if(user==null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    			
    		}
    		if(!"BIDDER".equalsIgnoreCase(user.getRole().getRolename())) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    			
    		}
    		biddingModel.setBidderId(user.getId());
    		biddingModel.setDateOfBidding(getTime());
    		biddingRepository.save(biddingModel);
    		return new ResponseEntity<>(biddingModel , HttpStatus.CREATED);
    		
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	

   //to get the bidding details which are greater than the given bidAmount
   //ok()->200

	//badRequest()->400
    public ResponseEntity<Object> getBidding(double bidAmount) {

    	try {
    		ArrayList<BiddingModel> bids  =  biddingRepository.getByBidAmountGreaterThan(bidAmount);
    		if(bids.isEmpty()) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		return new ResponseEntity<>(bids, HttpStatus.OK);

    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
    
    
    //to update the bidding status
    //ok->200
    //badRequest->400
    public ResponseEntity<Object> updateBidding (int id, BiddingModel model) {
    	try {
    		UserDetails userDetails =  getUser();
    		if(userDetails == null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		UserModel user =  userService.getUserByUsername(userDetails.getUsername());
    		if(user==null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    			
    		}
    		if("BIDDER".equalsIgnoreCase(user.getRole().getRolename())) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    			
    		}
    		BiddingModel bid  =  biddingRepository.findById(id).get();
    		if(bid == null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		bid.setStatus(model.getStatus());
    		biddingRepository.save(bid);
    		return new ResponseEntity<>(bid , HttpStatus.OK);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }

   //to delete the Bidding by using id
   //approver and only the creater of that particular Bidding can delete
   //noContent->284
   //badRequest->400
   //forbidden->403
    public ResponseEntity<Object> deleteBidding(int id){
    	try {
    		UserDetails userDetails =  getUser();
    		if(userDetails == null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		UserModel user =  userService.getUserByUsername(userDetails.getUsername());
    		if(user==null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    			
    		}
    		BiddingModel bid =  biddingRepository.findById(id).get();
    		System.out.println(bid);
    		if(bid == null) {
    			return new ResponseEntity<>("not found" , HttpStatus.BAD_REQUEST);
    		}
    		if("APPROVER".equals(user.getRole().getRolename()) || bid.getBidderId()==user.getId()) {
    			biddingRepository.delete(bid);
    			return new ResponseEntity<>("deleted Successfully" , HttpStatus.OK);
    		}
    		return new ResponseEntity<>("you do not have permission" , HttpStatus.BAD_REQUEST);
    		
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
}