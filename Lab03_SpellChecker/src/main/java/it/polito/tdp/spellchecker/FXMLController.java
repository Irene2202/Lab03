package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLanguage;

    @FXML
    private TextArea txtTesto;

    @FXML
    private TextArea txtErrori;

    @FXML
    private Text txtNumeroErrori;

    @FXML
    private Text txtRunTime;

    @FXML
    void doClearText(ActionEvent event) {
    	txtTesto.clear();
    	txtErrori.clear();
    	txtNumeroErrori.setText("");  	
    	txtRunTime.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long tempoIn=System.nanoTime();
    	
    	this.model.loadDictionary(boxLanguage.getValue());
    	
    	List<String> words= new ArrayList<>(); 
    	String parole = this.txtTesto.getText().toLowerCase();
    	parole = parole.replaceAll("[^a-zA-Z]+"," ");
    	String[] arrayParole=parole.split(" ");
    	for(String s: arrayParole)
    		words.add(s);
    	
    	List<RichWord> richWordErrate=new ArrayList<>();
    	richWordErrate=this.model.spellCheckText(words);
    	
    	for(RichWord r:richWordErrate) {
    		txtErrori.appendText(r.getParola()+"\n");
    	}
    	
    	txtNumeroErrori.setText("The text contains "+Integer.toString(richWordErrate.size())+" errors");
    	
    	long tempoFin=System.nanoTime()-tempoIn;
    	txtRunTime.setText("Tempo impiegato per lo Spell Check(in nanosecondi): "+tempoFin);

    }

    @FXML
    void initialize() {
        assert boxLanguage != null : "fx:id=\"language\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumeroErrori != null : "fx:id=\"txtNumeroErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRunTime != null : "fx:id=\"txtRunTime\" was not injected: check your FXML file 'Scene.fxml'.";
    }

	public void setModel(Dictionary model) {
		this.model=model;
		boxLanguage.getItems().addAll("English", "Italian");
	}
}
