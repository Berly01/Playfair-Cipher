package com.berly.unsa.cryptography.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MatrixController {

	@FXML private GridPane gridPaneMatrix;
	
	@FXML private Label labelKey;
		
	public void setMatrix(char[][] matrix, int size) {			
		for (int row = 0; row < size; row++) {		
			for (int col = 0; col < size; col++) {			
				var label = new Label(Character.toString(matrix[row][col]));		
				label.setFont(Font.font(18));
				
				gridPaneMatrix.add(label, col, row);
			}
		}		
	}
	
	public void setKey(String key) {
		labelKey.setText(String.format("Key: %s", key));
	}
}

