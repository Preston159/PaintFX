package com.ppetrie.paintfx;

import java.io.File;

import com.ppetrie.paintfx.events.PaintEventType;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Paint extends Application {
	
	/**
	 * The starting width/height of the stage
	 */
	private static final int WIDTH = 800, HEIGHT = 600;
	
	/**
	 * The file to be opened when the program is opened
	 * Remains null unless a command-line argument containing a file name is passed to the program
	 */
	private static File startFile = null;
	
	/**
	 * The primary stage of the program
	 */
	private Stage primaryStage;
	
	/**
	 * The controller of the program
	 */
	private PaintController controller = null;
	
	/**
	 * The program's image handler
	 */
	private ImageHandler imageHandler = null;
	
	/**
	 * The text in the title bar of the window corresponding to the file name
	 */
	private String title;
	
	/**
	 * The string to be appended to the title bar
	 */
	private final String titleAppend = " - PaintFX";
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		Platform.setImplicitExit(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				controller.getEventListener().handleType(PaintEventType.EXIT);
			}
		});
		
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("application.fxml").openStream());
			controller = loader.getController();
			
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		controller.init(this);
		imageHandler = new ImageHandler(this);
		
		if(startFile != null) {
			imageHandler.setImage(startFile);
		}
	}
	
	/**
	 * Launch the program
	 * @param args	args[0] will be interpreted as a file and PaintFX will attempt to open that file on launch
	 */
	public static void main(String[] args) {
		if(args.length > 0) {
			startFile = new File(args[0]);
			if(!startFile.exists()) {
				startFile = null;
			}
		}
		launch(new String[0]);
	}
	
	/**
	 * @return	the controller
	 */
	public PaintController getController() {
		return this.controller;
	}
	
	/**
	 * @return	the main stage
	 */
	public Stage getStage() {
		return this.primaryStage;
	}
	
	/**
	 * @return	the image handler
	 */
	public ImageHandler getImageHandler() {
		return this.imageHandler;
	}
	
	/**
	 * Sets the name of the current file, for use in the window title
	 * @param name	the name of the current file
	 */
	public void setFileName(String name) {
		primaryStage.setTitle(name + titleAppend);
		title = name;
	}
	
	/**
	 * Update the unsaved changes indicator (*) in the window title
	 * @param unsaved	whether the image has been changed since last save
	 */
	public void setUnsaved(boolean unsaved) {
		if(unsaved) {
			primaryStage.setTitle(title + "*" + titleAppend);
		} else {
			primaryStage.setTitle(title + titleAppend);
		}
	}
	
}
