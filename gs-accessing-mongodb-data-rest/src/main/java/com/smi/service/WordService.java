package com.smi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;
import com.smi.model.Language;
import com.smi.model.User;
import com.smi.model.Word;
import com.smi.mongo.CRUDUser;
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
		return null;
	}
	
	public static void addNewWordDefinition(Word word){
		CRUDWord.updateWordLanguageMeanings(word);
	}

	public static DBObject findByLanguage(String language) {
		DBObject foundWord = CRUDWord.findWordByEnglishDefinition(language);
		return foundWord;
	}

	public static boolean isWordExist(Word word) {
		DBObject foundWord = CRUDWord.findWordByEnglishDefinition(word.getWordInEnglish());
		if (foundWord !=null){
			return true;
		} else {
			return false;
		}
	}

	public static void saveWord(Word word) {
		CRUDWord.insertQueryWord(word);
		
	}
	
	public static Word returnWordForGame(String wordInEnglish, String username){
		DBObject user = CRUDUser.findUserByUsername(username);
		DBObject word = CRUDWord.findWordByEnglishDefinition(wordInEnglish);
		User foundUser = new User();
		foundUser.setMainLanguage(user.get("mainLanguage").toString());
		foundUser.setLangToLearn(user.get("langToLearn").toString());
		Word neededWord = new Word();
		neededWord.setWordInMainLanguage(word.get("word_"+foundUser.getMainLanguage()+"."+foundUser.getMainLanguage()).toString());
		Map<String, Boolean> wordDefinition = new HashMap<String, Boolean>();
		wordDefinition.put(word.get("word_"+foundUser.getLangToLearn()+"."+foundUser.getLangToLearn()).toString(), true);
		//wordDefinition.putAll();
		//neededWord.setWord(word.get("word_"+foundUser.getLangToLearn()));
		return null;
	}

	public static void deleteWordByEn(String wordInEn) {
		CRUDWord.deleteWord(wordInEn);
		
	}

}
