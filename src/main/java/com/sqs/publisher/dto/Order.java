package com.sqs.publisher.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class Order {
	
	private String name;
	
	private String cardType;
	
	private int discount;
	
	private int price;

}
