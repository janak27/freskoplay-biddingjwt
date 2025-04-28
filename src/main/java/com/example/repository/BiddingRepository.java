package com.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.BiddingModel;


@Repository
public interface BiddingRepository extends JpaRepository<BiddingModel, Integer> {

	ArrayList<BiddingModel> getByBidAmountGreaterThan(double bidAmount);

}
