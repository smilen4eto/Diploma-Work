package Mongo;

import java.util.Iterator;
import java.util.List;

import Model.Language;
import Model.Word;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CRUDWord {
	public static void insertQueryWord(Word word){
		BasicDBObject doc = new BasicDBObject()
//				.append("language", word.getLang().getLanguageName())
				.append("wordInMainLang", word.getWordInMainLanguage())
		        .append("level", word.getLevel())
		        .append("timesAnsCorr", word.getTimesAnsCorr())
		        .append("timesAnsWrong", word.getTimesAnsWrong());
		Connector.wordColl.insert(doc);
	}
	
	public static void updateWordLanguageMeanings(Word word){
		String wordMeaning = null;
		for (String string : word.getWord().keySet()) {
			if(word.getWord().get(string).equals(true)){
				wordMeaning = string;
				break;
			}
		}
		word.getWord().remove(wordMeaning);
		BasicDBObject wordDetail = new BasicDBObject();
		wordDetail.put(word.getLang().getLanguageName(), wordMeaning);
		wordDetail.put("word_wrong", word.getWord().keySet());
			Connector.wordColl.update(new BasicDBObject("wordInEn", word.getWordInEnglish()),
			new BasicDBObject("$set", new BasicDBObject("word_lang", wordDetail)));
	}
	
	public static DBObject getWordInLanguage(String language, String mainLanguage){
		return null;
//		DBObject word = Connector.usersColl.findOne(new BasicDBObject("username", username));
		
	}
}
