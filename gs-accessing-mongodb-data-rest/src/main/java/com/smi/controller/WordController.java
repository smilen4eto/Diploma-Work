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

import com.smi.model.Word;
import com.smi.service.WordService;

@RestController
public class WordController {
	//-------------------Retrieve All Words--------------------------------------------------------
    
	@RequestMapping(value = "/word/", method = RequestMethod.GET)
	public ResponseEntity<List<Word>> listAllWords() {
		List<Word> words = WordService.findAllUsers();
//		if(words.isEmpty()){
//			return new ResponseEntity<List<Word>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//		}
		return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
	}
 
 
    //-------------------Retrieve Single Word--------------------------------------------------------
     
    @RequestMapping(value = "/word/{language}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Word> getWord(@PathVariable("language") String language) {
        System.out.println("Fetching Word in language " + language);
        Word word = WordService.findByLanguage(language);
        if (word == null) {
            System.out.println("Word in this language" + language + "does not exist");
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
     
    @RequestMapping(value = "/word/{wordInEnglish}", method = RequestMethod.PUT)
    public ResponseEntity<Word> updateWord(@PathVariable("wordInEnglish") String wordInEn, @RequestBody Word word) {
        System.out.println("Updating Word " + word);
         
        Word currentWord = WordService.findByLanguage(word.getLang().toString());
         
        if (currentWord==null) {
            System.out.println("Word with name " + wordInEn + " not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
 // -------Changes to be done
 //       currentWord.set(.getName());
//        currentUser.setAge(user.getAge());
 //       currentUser.setSalary(user.getSalary());
         
        WordService.updateWord(currentWord);
        return new ResponseEntity<Word>(currentWord, HttpStatus.OK);
    }
 
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/word/{wordInEn}", method = RequestMethod.DELETE)
    public ResponseEntity<Word> deleteWord(@PathVariable("wordInEn") String wordInEn) {
        System.out.println("Fetching & Deleting word " + wordInEn);
 
        Word word = WordService.findByLanguage(wordInEn);
        if (word == null) {
            System.out.println("Unable to delete. Word not found");
            return new ResponseEntity<Word>(HttpStatus.NOT_FOUND);
        }
 
        WordService.deleteWordByEn(wordInEn);
        return new ResponseEntity<Word>(HttpStatus.NO_CONTENT);
    }
 
     
//    //------------------- Delete All Users --------------------------------------------------------
//     
//    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
//    public ResponseEntity<User> deleteAllUsers() {
//        System.out.println("Deleting All Users");
// 
//        userService.deleteAllUsers();
//        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//    }
 

}
