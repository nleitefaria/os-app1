package com.mycompany.osapp1.domain;

public class TitleDTO {
	
	private Integer id;
	private String title;
	
	public TitleDTO() 
	{
	}
	
	public TitleDTO(Integer id, String title) 
	{		
		this.id = id;
		this.title = title;
	}
	
	public Integer getId() 
	{
		return id;
	}
	public void setId(Integer id) 
	{
		this.id = id;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
}
