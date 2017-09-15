package com.elastic.srb.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.elastic.srb.model.Ebook;
import com.elastic.srb.repository.EbookRepository;
import com.elastic.srb.service.EbookElasticService;
import com.elastic.srb.service.EbookService;

@Service
public class EbookServiceImpl implements EbookService {

	@Autowired
	EbookRepository ebookRep;
	
	@Autowired
	EbookElasticService ebookElastic;
	

	@Override
	public Ebook save(Ebook ebook) {
		// TODO Auto-generated method stub
		return ebookRep.save(ebook);
	}

	@Override
	public Ebook update(Long id, Ebook ebook) {
		// TODO Auto-generated method stub
		Ebook editedBook = ebookElastic.findOne(id);
		
		editedBook.setAuthor(ebook.getAuthor());
		editedBook.setCategory(ebook.getCategory());
		editedBook.setFilename(ebook.getFilename());
		editedBook.setImage(ebook.getImage());
		editedBook.setKeywords(ebook.getKeywords());
		editedBook.setLanguage(ebook.getLanguage());
		editedBook.setMimeBook(ebook.getMimeBook());
		editedBook.setPublication_year(ebook.getPublication_year());
		editedBook.setText(ebook.getText());
		editedBook.setTitle(ebook.getTitle());
		editedBook.setUser(ebook.getUser());
		
		return ebookElastic.save(save(editedBook));
	}

	@Override
	public Ebook findOne(Long id) {
		// TODO Auto-generated method stub
		return ebookRep.findOne(id);
	}

	@Override
	public Iterable<Ebook> findAll() {
		// TODO Auto-generated method stub
		return ebookRep.findAll();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ebookRep.delete(id);
	}

	@Override
	public void delete(Ebook ebook) {
		// TODO Auto-generated method stub
		ebookRep.delete(ebook);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		ebookRep.deleteAll();
	}

	@Override
	public Ebook uploadPDF(MultipartFile pdf) {

		File tempFile = null;
		try {
			tempFile = File.createTempFile("template", ".pdf", null);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tempFile);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			fos.write(pdf.getBytes());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(tempFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ParseContext pcontext = new ParseContext();

		// parsing the document using PDF parser
		PDFParser pdfparser = new PDFParser();
		try {
			pdfparser.parse(inputstream, handler, metadata, pcontext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		Ebook ebook = new Ebook();
		ebook.setText(handler.toString());
		// getting the content of the document
		System.out.println("Contents of the PDF ########### :" + handler.toString());

		// getting metadata of the document
		System.out.println("Metadata of the PDF ###########:");
		String[] metadataNames = metadata.names();

		for (String name : metadataNames) {
			System.out.println(name + " : " + metadata.get(name));
		}
		
		ebook.setAuthor(metadata.get("Author"));
		ebook.setTitle(metadata.get("title"));
		ebook.setKeywords(metadata.get("Keywords"));
		ebook.setPublication_year(metadata.get("created"));

		return ebook;
	}

}
