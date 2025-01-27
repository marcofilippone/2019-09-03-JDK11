/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;
	private boolean creato = false;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	if(!creato) {
    		txtResult.setText("Devi prima creare il grafo");
    		return;
    	}
    	String n = txtPassi.getText();
    	Integer N;
    	try {
    		N = Integer.parseInt(n);
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero intero di passi");
    		return;
    	}
    	String s = boxPorzioni.getValue();
    	if(s==null || s.equals("")) {
    		txtResult.setText("Scegliere un vertice dalla tendina");
    		return;
    	}
    	List<String> cammino = model.cammino(N, s);
    	txtResult.appendText("Cerco cammino peso massimo...\n\n");
    	if(cammino.size()==1) {
    		txtResult.appendText("Cammino non esistente");
    	} else {
    		for(String v : cammino) {
        		txtResult.appendText(v+"\n");
        	}
    		txtResult.appendText("\nPeso totale: "+model.getPesoTot());
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	if(!creato) {
    		txtResult.setText("Devi prima creare il grafo");
    		return;
    	}
    	String s = boxPorzioni.getValue();
    	if(s==null || s.equals("")) {
    		txtResult.setText("Scegliere un vertice dalla tendina");
    		return;
    	}
    	txtResult.appendText("Cerco porzioni correlate...\nVertici:\n\n");
    	for(Arco arco : model.getConnessi(s)) {
    		txtResult.appendText(arco.getB()+" - "+arco.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String c = txtCalorie.getText();
    	Integer cal;
    	try {
    		cal = Integer.parseInt(c);
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero intero di calorie");
    		return;
    	}
    	if(creato) {
    		boxPorzioni.getItems().removeAll(model.getVertexes());
    	}
    	model.creaGrafo(cal);
    	creato = true;
    	txtResult.appendText("Creazione grafo...\n"+model.getVertexes().size()+" vertici e "+model.getEdges().size()+" archi");
    	boxPorzioni.getItems().addAll(model.getVertexes());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
