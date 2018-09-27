package com.mycompany.osapp1.domain;

public class ProductLineDTO 
{
	private String productLine;
    private String textDescription;
      
	public ProductLineDTO()
	{		
	}

	public ProductLineDTO(String productLine, String textDescription) 
	{		
		this.productLine = productLine;
		this.textDescription = textDescription;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getTextDescription() {
		return textDescription;
	}

	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

}
