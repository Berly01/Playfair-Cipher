package com.berly.unsa.cryptography.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.berly.unsa.cryptography.controller.HomeController;

public class Home extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		var fxmlLoader = new FXMLLoader(Home.class.getResource("/com/berly/unsa/cryptography/gui/home.fxml"));
		Parent root = fxmlLoader.load();

		stage.getIcons().add(new Image(Home.class.getResourceAsStream("/com/berly/unsa/cryptography/image/home.png")));
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.setScene(new Scene(root));
		stage.setTitle("Playfair Cipher");

		stage.setOnCloseRequest(event -> {
			HomeController homeController = fxmlLoader.getController();
			homeController.closeMatrixStage();
		});

		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}