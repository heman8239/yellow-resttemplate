package com.yellow.resttemplate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.yellow.resttemplate.vo.Book;

@Controller
public class BookController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final String BASE_URL = "http://localhost:8080";
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/listBook")
	public String viewBookList(Model model) {
        ResponseEntity<List<Book>> responseEntity = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}
        );
		List<Book> books = responseEntity.getBody();
		model.addAttribute("allBooks", books);
		
		return "listBook";
	}
	
	@GetMapping("/viewBook/{id}")
	public String viewBook(@PathVariable("id") String id, Model model) {
        ResponseEntity<Book> responseEntity = restTemplate.getForEntity(
                BASE_URL + "/" + id,
                Book.class
        );
		Book book = responseEntity.getBody();
		model.addAttribute("book", book);
		
		return "viewBook";
	}
	
	@GetMapping("/addViewBook")
	public String addViewBook() {
		return "addViewBook";
	}
	
	@PostMapping("/addBook")
	public String addBook(@ModelAttribute Book book) {
        restTemplate.postForEntity(
                BASE_URL,
                book,
                Book.class
        );
		
		return "redirect:/listBook";
	}
	
	@GetMapping("/updateViewBook/{id}")
	public String updateViewBook(@PathVariable("id") String id, Model model) {
        ResponseEntity<Book> responseEntity = restTemplate.getForEntity(
                BASE_URL + "/" + id,
                Book.class
        );
		Book book = responseEntity.getBody();
		model.addAttribute("book", book);
		
		return "updateViewBook";
	}
	
	@PostMapping("/updateBook/{id}")
	public String updateBook(@PathVariable("id") String id, @ModelAttribute Book book) {
        restTemplate.put(
                BASE_URL + "/" + id,
                book
        );
		
		return "redirect:/listBook";
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") String id) {
        restTemplate.delete(
                BASE_URL + "/" + id
        );
		
		return "redirect:/listBook";
	}

}