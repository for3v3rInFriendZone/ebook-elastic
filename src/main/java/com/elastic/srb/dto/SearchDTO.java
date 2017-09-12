package com.elastic.srb.dto;

public class SearchDTO {

	private String field;
	private String value;
	private String operator;
	private Boolean language;

	public SearchDTO() {

	}
	
	public SearchDTO(String field, String value, String operator) {
		super();
		this.field = field;
		this.value = value;
		this.operator = operator;
	}

	public SearchDTO(String field, String value, String operator, Boolean language) {
		super();
		this.field = field;
		this.value = value;
		this.operator = operator;
		this.language = language;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getLanguage() {
		return language;
	}

	public void setLanguage(Boolean language) {
		this.language = language;
	}

}
