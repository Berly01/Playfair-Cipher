package com.berly.unsa.cryptography.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.berly.unsa.cryptography.controller.MatrixController;
import com.berly.unsa.cryptography.gui.Home;
import com.berly.unsa.cryptography.model.PlayfairCipher;

public abstract class StageFactory {

	private StageFactory() {}
	
	public static Stage createVisualizatorOfPlayfairCipherMatrix(char[][] matrix, String key) throws IOException {	
		var fxmlLoader = new FXMLLoader(StageFactory.class.getResource("/com/berly/unsa/cryptography/gui/matrix.fxml"));
		Parent root = fxmlLoader.load();
		
		MatrixController matrixController = fxmlLoader.getController();
        matrixController.setMatrix(matrix, PlayfairCipher.MATRIX_SIZE);
		matrixController.setKey(key);
		
		var stage = new Stage();    
		stage.getIcons().add(new Image(Home.class.getResourceAsStream("/com/berly/unsa/cryptography/image/matrix.png")));
		stage.setResizable(false);
		stage.setFullScreen(false);
        stage.setTitle("Playfair Cipher Matrix");
        stage.setScene(new Scene(root));
        return stage;	
	}
}