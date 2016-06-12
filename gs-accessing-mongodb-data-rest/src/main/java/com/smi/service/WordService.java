package com.smi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smi.model.Language;
import com.smi.model.Word;
import com.smi.mongo.CRUDWord;

public class WordService {

	public static List<Word> findAllUsers() {
//		Word word = new Word();
//		word.setLang(new Language("ES"));
//		word.setLevel(1);
//		Map<String, Boolean> wordInEs = new HashMap<String, Boolean>();
//		wordInEs.put("appleso", false);
//		wordInEs.put("manzana", true);
//		wordInEs.put("apple", false);
//		wordInEs.put("balana", false);
//		word.setWord(wordInEs);
//		word.setWordInEnglish("apple");
//		CRUDWord.updateWordLanguageMeanings(word);
		
		Word word = new Word();
		word.setLang(new Language("EN"));
		word.setLevel(1);
		Map<String, Boolean> wordInEn = new HashMap<String, Boolean>();
		wordInEn.put("apple", true);
		wordInEn.put("llablaka", false);
		wordInEn.put("apriot", false);
		wordInEn.put("fruit", false);
		word.setWord(wordInEn);
		word.setWordInEnglish("apple");
		CRUDWord.updateWordLanguageMeanings(word);
		return null;
	}

	public static Word findByLanguage(String language) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean isWordExist(Word word) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void saveWord(Word word) {
		CRUDWord.insertQueryWord(word);
		
	}

	public static void updateWord(Word currentWord) {
		// TODO Auto-generated method stub
		
	}

	public static void deleteWordByEn(String wordInEn) {
		// TODO Auto-generated method stub
		
	}

}
