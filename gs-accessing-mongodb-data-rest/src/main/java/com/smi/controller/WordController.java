package com.smi.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mongodb.DBObject;
import com.smi.model.Language;
import com.smi.model.User;
import com.smi.model.Word;
import com.smi.service.WordService;

@RestController
public class WordController {
	//-------------------Retrieve All Words--------------------------------------------------------
    
//	@RequestMapping(value = "/word/", method = RequestMethod.GET)
//	public ResponseEntity<List<Word>> listAllWords() {
//		List<Word> words = WordService.findAllUsers();
////		if(words.isEmpty()){
////			return new ResponseEntity<List<Word>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
////		}
//		return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
//	}
// 
 
    //-------------------Retrieve Single Word--------------------------------------------------------
	
	//TBD!!!!!!!!
     
    @RequestMapping(value = "/word/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Word> getWord(@PathVariable("username") String username) {
        System.out.println("Fetching Word for user " + username);
        Word word = WordService.returnWordForGame("", username);
        if (word == null) {
            System.out.println("Word " + "" + "does not exist");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Word>(word, HttpStatus.OK);
    }
 

     
    //-------------------Create a Word-------------------------------------------------------- ok
     
    @RequestMapping(value = "/word/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createWord(@RequestBody Word word, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Word " + word.getWordInEnglish());
 
        if (WordService.isWordExist(word)) {
            System.out.println("A Word with name " + word.getWordInEnglish() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        WordService.saveWord(word);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/word/{language}").buildAndExpand(word.getWordInEnglish()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a Word --------------------------------------------------------
     
    @RequestMapping(value = "/word/{language}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Word> updateWord(@PathVariable("language") String language, @RequestBody Word word) {
        System.out.println("Updating Word " + word.getWordInEnglish());
         
        DBObject currentWord = WordService.findByLanguage(word.getWordInEnglish());
         
        if (currentWord==null) {
            System.out.println("Word with name " + currentWord + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
        
        word.setLang(new Language(language));
         
        WordService.addNewWordDefinition(word);
        return new ResponseEntity<Word>(word, HttpStatus.OK);
    }
 
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/word/{wordInEn}", method = RequestMethod.DELETE)
    public ResponseEntity<Word> deleteWord(@PathVariable("wordInEn") String wordInEn) {
        System.out.println("Fetching & Deleting word " + wordInEn);
 
        DBObject word = WordService.findByLanguage(wordInEn);
        if (word == null) {
            System.out.println("Unable to delete. Word not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
 
        WordService.deleteWordByEn(wordInEn);
        return new ResponseEntity<Word>(HttpStatus.NO_CONTENT);
    }
 
     
 

}
