package com.berly.unsa.cryptography.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;

import com.berly.unsa.cryptography.model.EmptyRadioButtonException;
import com.berly.unsa.cryptography.model.PlayfairCipher;
import com.berly.unsa.cryptography.model.StringFormatException;
import com.berly.unsa.cryptography.util.StageFactory;

public class HomeController {

	@FXML private TextArea inputMessage;
	
	@FXML private TextArea outputMessage;
	
	@FXML private TextField inputKey;
	
	@FXML private ToggleGroup tougleGroupOfRadioButtons;
	
	private Stage matrixStage = null;
	
	private PlayfairCipher playfairCipher = new PlayfairCipher();
	
	private static final String EMPTY_RADIO_BUTTON = "Select one letter, I or J";
	
	private static final String EMPTY_KEY_OR_MESSAGE = "The message and key have to contain letters or white spaces";
	
	public void onActionDecrypt() {
		try {				
			var optionalRadioButton = getRadioButtonSelected();
			String selectedLetter;
			
			if (optionalRadioButton.isEmpty()) {
				throw new EmptyRadioButtonException();
			} else {
				selectedLetter = optionalRadioButton.get().getText();
			}
					
			var message = inputMessage.getText();
			var key = inputKey.getText();			
							
			String decryptedMessage;
			
			if (selectedLetter.compareTo(String.valueOf(PlayfairCipher.LETTER_I)) == 0) {
				decryptedMessage = playfairCipher.decryptWithLetterI(message, key);
			} else {
				decryptedMessage = playfairCipher.decryptWithLetterJ(message, key);
			}
			
			outputMessage.setText(decryptedMessage);
		} catch (StringFormatException e) {
			e.printStackTrace();
			var alert = new Alert(AlertType.ERROR, EMPTY_KEY_OR_MESSAGE);
			alert.show();	
		} catch (EmptyRadioButtonException e) {
			e.printStackTrace();
			var alert = new Alert(AlertType.ERROR, EMPTY_RADIO_BUTTON);
			alert.show();	
		}
	}
	
	public void onActionEncrypt() {	
		try {				
			var optionalRadioButton = getRadioButtonSelected();
			String selectedLetter;
			
			if (optionalRadioButton.isEmpty()) {
				throw new EmptyRadioButtonException();
			} else {
				selectedLetter = optionalRadioButton.get().getText();
			}
			
			var message = inputMessage.getText();
			var key = inputKey.getText();
				
			String encryptedMessage;
			
			if (selectedLetter.compareTo(String.valueOf(PlayfairCipher.LETTER_I)) == 0) {
				encryptedMessage = playfairCipher.encryptWithLetterI(message, key);
			} else {
				encryptedMessage = playfairCipher.encryptWithLetterJ(message, key);
			}
					
			outputMessage.setText(encryptedMessage);
		} catch (StringFormatException e) {
			e.printStackTrace();
			var alert = new Alert(AlertType.ERROR, EMPTY_KEY_OR_MESSAGE);
			alert.show();	
		} catch (EmptyRadioButtonException e) {
			e.printStackTrace();
			var alert = new Alert(AlertType.ERROR, EMPTY_RADIO_BUTTON);
			alert.show();	
		}
	}
	
	public void onActionSeeMatrix() throws IOException {
		try {
			var optionalRadioButton = getRadioButtonSelected();
			String selectedLetter;
			
			if (optionalRadioButton.isEmpty()) {
				throw new EmptyRadioButtonException();
			} else {
				selectedLetter = optionalRadioButton.get().getText();
			}
			
			var key = inputKey.getText();
					
			char[][] matrix;
			
			if (selectedLetter.compareTo(String.valueOf(PlayfairCipher.LETTER_I)) == 0) {
				matrix = PlayfairCipher.createMatrixWithI(key);
			} else {
				matrix = PlayfairCipher.createMatrixWithJ(key);
			}
			
			closeMatrixStage();
			
			matrixStage = StageFactory.createVisualizatorOfPlayfairCipherMatrix(matrix, key);			
			matrixStage.show();
			
		} catch (StringFormatException e) {
			e.printStackTrace();
			var alert = new Alert(AlertType.ERROR, EMPTY_KEY_OR_MESSAGE);
			alert.show();	
		} catch (EmptyRadioButtonException e) {
			e.printStackTrace();
			var alert = new Alert(AlertType.ERROR, EMPTY_RADIO_BUTTON);
			alert.show();	
		}
	}	
	
	public void closeMatrixStage() {
		if (matrixStage != null) {
			matrixStage.close();
			matrixStage = null;
		}
	}
	
	private Optional<RadioButton> getRadioButtonSelected() {		
		var selectedRadioButton = (RadioButton) tougleGroupOfRadioButtons.getSelectedToggle();		
		return Optional.ofNullable(selectedRadioButton);	
	}
}