package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.BiddingModel;
import com.example.service.BiddingService;

// import com.fresco.tenderManagement.model.BiddingModel;
//
//
// import com.fresco.tenderManagement.service. BiddingService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
// import org.springframework.http.ResponseEntity;
//
// import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bidding")
public class BiddingController {

	/*
	 * This controller would be responsible for the BiddingController endpoints Add
	 * the required annotations and create the endpoints
	 */

	@Autowired
	private BiddingService biddingService;

	// to create a bidding using biddingModel object
	@PostMapping("/add")
		public ResponseEntity<Object> postBidding (@RequestBody  BiddingModel biddingModel){
		
		return biddingService.postBidding(biddingModel);
		}

	// to get the bidding which are greater than the given bidAmount
	@GetMapping("/list")
	public ResponseEntity<Object> getBidding(@RequestParam double bidAmount) {
		return biddingService.getBidding(bidAmount);
	}

	// to update the bidding by id as PathVariable and bidding Object
	@PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateBidding(@PathVariable int id, @RequestBody BiddingModel biddingModel) {
		return biddingService.updateBidding(id, biddingModel);
	}

	// to delete the bidding by using id as PathVariable
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteBidding(@PathVariable int id) {
		return biddingService.deleteBidding(id);
	}
}
