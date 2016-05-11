package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.db.PortoDAO;
import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;
import it.polito.tdp.porto.model.PortoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	PortoModel model = new PortoModel();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxAutore1;

    @FXML
    private ComboBox<String> boxAutore2;

    @FXML
    private Button btnCoautori;

    @FXML
    private Button btnCluster;

    @FXML
    private Button btnArticoli;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArticoli(ActionEvent event) {
    	/*
    	txtResult.setText("");
    	Autore uno = model.trovaAutorePerNome(boxAutore1.getValue());
    	Autore due = model.trovaAutorePerNome(boxAutore2.getValue());
    	
    	txtResult.appendText("Titoli:\n");
    	
    	for(Articolo a : uno.articoli)
    		for(Articolo b : due.articoli)
    			if(a.getId()==b.getId())
    				txtResult.appendText(""+a.getTitolo()+"\n");
    	*/
    	txtResult.setText("");
    	
    	List<Articolo> articoli = new LinkedList<Articolo>();
    	
    	txtResult.appendText("Titoli degli Articoli che permettono la connessione:\n");
    	model.camminoMinimo(boxAutore1.getValue(), boxAutore2.getValue());
    	articoli.addAll(model.articoliConnessi());
    	
    	for(Articolo a : articoli){
    		if(a!=null)
    			txtResult.appendText("- "+ a.getTitolo() +"\n");
		}
    	
    	
    }

    @FXML
    void doCluster(ActionEvent event) {
    	txtResult.setText("");
    	model.cluster(model.visitati);
    	this.stampaCluster(model.cluster);
    	txtResult.appendText("Numero di cluster trovati: "+ model.cont);
    }

    @FXML
    void doCoautori(ActionEvent event) {
    	
    	txtResult.setText("");
    	
    	if(boxAutore1.getValue()==null && boxAutore2.getValue()!=null){
    		stampaCoautori(model.Coautori(boxAutore2.getValue()));
    		return;
    	}	
    	if(boxAutore1.getValue()!=null && boxAutore2.getValue()==null){
    		stampaCoautori(model.Coautori(boxAutore1.getValue()));
    		return;
    	}
    	
    	txtResult.appendText("------------ERRORE!------------");
    	
    }
    
    public void stampaCluster(List<List<Autore>> cluster) {
    	//List<Autore> temp = new LinkedList<Autore>();
		for(List<Autore> list : cluster){
			txtResult.appendText("Nuovo cluster: \n");
			for(Autore a : list){
				txtResult.appendText("Cognome: "+ a.getCognome() +"; Nome: "+ a.getNome()+"\n");
			}
		}
	}


	private void stampaCoautori(List<Autore> coautori) {
		for(Autore a : coautori)
			txtResult.appendText("Cognome: "+ a.getCognome() +"; Nome: "+ a.getNome()+"\n");
	}

	@FXML
    void initialize() {
        assert boxAutore1 != null : "fx:id=\"boxAutore1\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxAutore2 != null : "fx:id=\"boxAutore2\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCoautori != null : "fx:id=\"btnCoautori\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCluster != null : "fx:id=\"btnCluster\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnArticoli != null : "fx:id=\"btnArticoli\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        
        PortoDAO p = new PortoDAO();
        
        model.creaGrafo();
        
        List<Autore> lista = new LinkedList<Autore>();
        lista.addAll(p.getAutori());
        
        for(Autore a : lista){
        	boxAutore1.getItems().add(a.getCognome());
        	boxAutore2.getItems().add(a.getCognome());
        }
    }

	
}