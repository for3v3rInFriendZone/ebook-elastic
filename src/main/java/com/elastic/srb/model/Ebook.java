package com.elastic.srb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Entity
@Table(name = "EBOOK")
@Document(indexName = "books", type = "books", shards = 1)
public class Ebook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "KEYWORDS")
	private String keywords;

	@Transient
	private String text;

	@Column(name = "PUBLICATION_YEAR")
	private String publication_year;

	@Column(name = "PROFILE_IMAGE", length = 10485760)
	private String image;

	@Column(name = "FILENAME")
	private String filename;

	@Column(name = "MIME")
	private String mimeBook;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY", nullable = false)
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LANGUAGE", nullable = false)
	@Field(type = FieldType.Nested)
	private Language language;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EBOOKUSER", nullable = false)
	private User user;

	public Ebook() {

	}

	public Ebook(String title, String author, String keywords, String image, String publication_year, String filename,
			String mimeBook) {
		super();
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.image = image;
		this.publication_year = publication_year;
		this.filename = filename;
		this.mimeBook = mimeBook;
	}

	public Ebook(String title, String author, String keywords, String text, Language language) {
		super();
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.text = text;
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getPublication_year() {
		return publication_year;
	}

	public void setPublication_year(String publication_year) {
		this.publication_year = publication_year;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimeBook() {
		return mimeBook;
	}

	public void setMimeBook(String mimeBook) {
		this.mimeBook = mimeBook;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
