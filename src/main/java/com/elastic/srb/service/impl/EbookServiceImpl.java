package com.elastic.srb.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.elastic.srb.dto.EbookDTO;
import com.elastic.srb.elasticRepository.EbookElasticRepository;
import com.elastic.srb.model.Ebook;
import com.elastic.srb.repository.EbookRepository;
import com.elastic.srb.service.EbookElasticService;
import com.elastic.srb.service.EbookService;

@Service
public class EbookServiceImpl implements EbookService {

	@Autowired
	EbookRepository ebookRep;
	
	@Autowired
	private EbookElasticRepository ebookElastic;
	

	@Override
	public Ebook save(Ebook ebook) {
		// TODO Auto-generated method stub
		return ebookRep.save(ebook);
	}

	@Override
	public Ebook update(Long id, Ebook ebook) {
		// TODO Auto-generated method stub
		Ebook editedBook = toEbook(ebookElastic.findOne(id));
		
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
		
		return toEbook(ebookElastic.save(toEbookDTO(save(editedBook))));
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
		File fileLocation = new File("C:/Users/Marko/git/ebook-elastic/src/main/resources/static/assets/PdfStorage");
		try {
			tempFile = File.createTempFile("template", ".pdf", fileLocation);
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
		ebook.setFilename(tempFile.getName());
		ebook.setMimeBook("application/pdf");
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

	@Override
	public Ebook toEbook(EbookDTO ebookDto) {
		// TODO Auto-generated method stub
		Ebook ebook = findOne(ebookDto.getId());
		ebook.setText(ebookDto.getText());
		ebook.setHighlight(ebookDto.getHighlight());
		
		return ebook;
	}

	@Override
	public EbookDTO toEbookDTO(Ebook ebook) {
		EbookDTO ebookDto = new EbookDTO();
		
		ebookDto.setId(ebook.getId());
		ebookDto.setAuthor(ebook.getAuthor());
		ebookDto.setKeywords(ebook.getKeywords());
		ebookDto.setLanguageName(ebook.getLanguage().getName());
		ebookDto.setPublication_year(ebook.getPublication_year());
		ebookDto.setText(ebook.getText());
		ebookDto.setTitle(ebook.getTitle());
		
		return ebookDto;
	}

}
