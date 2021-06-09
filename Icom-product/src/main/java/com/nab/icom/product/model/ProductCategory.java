package com.nab.icom.product.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductCategory {

	@Id
	private String id;
	private String name;
	private String title;
	private String description;
	private String imgUrl;	

	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + ", title=" + title + ", description=" + description
				+ ", imgUrl=" + imgUrl + "]";
	}

}