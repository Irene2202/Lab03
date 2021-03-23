package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dictionary {
	
	Set<String> dictionary;
	
	
	
	public Dictionary() {
		dictionary=new HashSet<>();
	}

	/**
	 * Carica in memoria il dizionario della lingua selezionata
	 * @param language
	 */
	public void loadDictionary(String language) {
		dictionary.clear();

		if(language.equals("English")) {
			try {
				FileReader fr=new FileReader("English.txt");
				BufferedReader br=new BufferedReader(fr);
				String word;
				while((word=br.readLine())!= null) {
					dictionary.add(word.toLowerCase());
				}
				
				br.close();
				fr.close();
			} catch(IOException e) {
				//System.out.println("Errore nella lettura da file");
				throw new RuntimeException("Errore nella lettura da file", e);
			}
		}
		else {
			try {
				FileReader fr=new FileReader("Italian.txt");
				BufferedReader br=new BufferedReader(fr);
				String word;
				while((word=br.readLine())!= null) {
					dictionary.add(word.toLowerCase());
				}
				
				br.close();
				fr.close();
			} catch(IOException e) {
				//System.out.println("Errore nella lettura da file");
				throw new RuntimeException("Errore nella lettura da file", e);
			}
		}
		
	}
	
	/**
	 * Esegue controllo ortografico su testo in input, passato come Lista parole
	 * e restituisce la Lista delle RichWord; esse saranno
	 * -> 'true' se la parola è presente nel dizionario 
	 * -> 'false' se la parola non è presente; ovvero se è errata
	 * @param inputTextList
	 * @return
	 */
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> paroleErrate=new ArrayList<>();
		RichWord parola;
		
		for(String s:inputTextList) {
			parola=new RichWord(s);
			if(dictionary.contains(s)) {
				parola.setCorretta(true);
			}
			
			if(!parola.isCorretta())
				paroleErrate.add(parola);			
		}
		
		return paroleErrate;
	}

}

