package me.ppetrie.paint;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.ppetrie.paint.events.PaintEventType;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Paint extends Application {
	
	private static final int WIDTH = 800, HEIGHT = 600;
	
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
	 * The text in the title bar of the window, excluding the "*" if unsaved
	 */
	String title;
	
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
		primaryStage.setTitle(name);
		title = name;
	}
	
	/**
	 * Update the unsaved changes indicator (*) in the window title
	 * @param unsaved	whether the image has been changed since last save
	 */
	public void setUnsaved(boolean unsaved) {
		if(unsaved) {
			primaryStage.setTitle(title + " *");
		} else {
			primaryStage.setTitle(title);
		}
	}
	
}
