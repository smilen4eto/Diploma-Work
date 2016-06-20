package com.smi.mongo;



import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.smi.model.Word;

public class CRUDWord {
	public static void insertQueryWord(Word word){
		BasicDBObject doc = new BasicDBObject()
				.append("wordInEn", word.getWordInEnglish())
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
		wordDetail.put("word_wrong"+word.getLang().getLanguageName() , word.getWord().keySet());
			Connector.wordColl.update(new BasicDBObject("wordInEn", word.getWordInEnglish()),
			new BasicDBObject("$set", new BasicDBObject("word_"+word.getLang().getLanguageName(), wordDetail)));
	}
	
//	public static DBObject getWordInLanguage(String wordInEnglish,String languageToLearn, String mainLanguage){
//		DBObject word = Connector.wordColl.findOne(new BasicDBObject("wordInEn", wordInEnglish));
//		
//	}

	public static DBObject findWordByEnglishDefinition(String wordInEnglish) {
		DBObject word = Connector.wordColl.findOne(new BasicDBObject("wordInEn", wordInEnglish));
		return word;
	}

	public static void deleteWord(String wordInEn) {
		Connector.wordColl.remove(new BasicDBObject("wordInEn", wordInEn));
	}
}
