package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {
	
	//Set<String> dictionary;
	List<String> dictionary;
	
	String language;
	
	
	public Dictionary() {
		//dictionary=new HashSet<>();
		//dictionary=new LinkedList<>();
		dictionary=new ArrayList<>();
		
		this.language="";
	}

	/**
	 * Carica in memoria il dizionario della lingua selezionata
	 * @param language
	 */
	public void loadDictionary(String language) {
		if(this.language.equals(language))
			return ;
		
		this.language=language;
		dictionary.clear();

		if(language.equals("English")) {
			try {
				FileReader fr=new FileReader("src/main/resources/English.txt");
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
				FileReader fr=new FileReader("src/main/resources/Italian.txt");
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
	
	/**
	 * Esegue lo Spell Check con una ricerca lineare
	 * @param inputTextList
	 * @return
	 */
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List<RichWord> paroleErrate=new ArrayList<>();
		RichWord parola;
		
		for(String s:inputTextList) {
			parola=new RichWord(s);
			
			for(String si:dictionary) {
				if(si.equals(s)) {
					parola.setCorretta(true);
					break;
				}
			}
			
			if(!parola.isCorretta())
				paroleErrate.add(parola);			
		}
		
		return paroleErrate;
	}
	
	/**
	 * Esegue lo Spell Check con una ricerca dicotomica
	 * @param inputTextList
	 * @return
	 */
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		List<RichWord> paroleErrate=new ArrayList<>();
		RichWord parola;
		int index;
		
		for(String s:inputTextList) {
			parola=new RichWord(s);
			//il metodo restituisce l'indice a cui viene trovato l'elemento nella lista 'dictionary'
			//eseguendo una ricerca dicotomica; se non lo trova l'indice assume valori negativi
			index=Collections.binarySearch(dictionary, s);
			if(index>=0)
				parola.setCorretta(true);
			
			if(!parola.isCorretta())
				paroleErrate.add(parola);			
		}
		
		return paroleErrate;
	}

}

