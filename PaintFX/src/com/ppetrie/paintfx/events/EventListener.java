package com.ppetrie.paintfx.events;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ppetrie.paintfx.Paint;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class EventListener{
	
	private Paint paint;
	
	private static final ExtensionFilter extensions = new ExtensionFilter("Image Files", //list of valid extensions
			"*.jpg",
			"*.jpeg",
			"*.bmp",
			"*.png"
	);
	
	public EventListener(Paint paint) {
		this.paint = paint;
	}
	
	/**
	 * Handle an event
	 * @param eventType	the type of event to handle
	 */
	public void handleType(PaintEventType eventType) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extensions);
		
		File chosen = null; //file chosen by user
		
		boolean save = false; //whether to save rather than save as
		
		switch(eventType) {
		case OPEN: //open a file
			fileChooser.setTitle("Open Image");
			chosen = fileChooser.showOpenDialog(paint.getStage());
			if(chosen == null) {
				break;
			}
			paint.getImageHandler().setImage(chosen);
			break;
		case NEW:
			paint.getImageHandler().setImage(null);
			break;
		case SAVE: //save the currently-opened file
			chosen = paint.getImageHandler().getCurrentImageFile();
			save = chosen != null; //show save as dialog if image was created in program
		case SAVE_AS: //save the current picture to a different file
			if(!save) {
				fileChooser.setTitle("Save Image");
				chosen = fileChooser.showSaveDialog(paint.getStage());
			}
			if(chosen == null) { //break if user did not choose file
				break;
			}
			FileOutputStream fos = null;
			try {
				if(!chosen.exists()) {
					chosen.createNewFile();
				}
				fos = new FileOutputStream(chosen);
				BufferedImage bi = SwingFXUtils.fromFXImage(paint.getImageHandler().getImage(), null);
				BufferedImage noAlpha = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
				noAlpha.getGraphics().drawImage(bi, 0, 0, null);
				String[] filePerSep = chosen.toString().split(("\\.")); //file name separated by periods
				String ext = filePerSep[filePerSep.length - 1];
				ImageIO.write(noAlpha, ext, fos);
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			paint.getImageHandler().setCurrentImageFile(chosen); //set current file
			save = false;
			paint.getImageHandler().setImageChanged(false);
			break;
		case EXIT: //exit the program
			if(paint.getImageHandler().getImageChanged()) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "The image has been changed. Are you sure you want to quit?",
						ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if(alert.getResult() == ButtonType.NO) {
					break;
				}
			}
			Platform.exit();
			break;
		case SWAP_COLORS: //swap the primary and secondary colors
			Color primary = paint.getController().getPrimaryColor();
			paint.getController().getPrimaryColorPicker().setValue(paint.getController().getSecondaryColor());
			paint.getController().getSecondaryColorPicker().setValue(primary); //fall through to update colors
		case UPDATE_COLORS: //update the colors
			paint.getController().getDrawCanvas().getGraphicsContext2D().setStroke(paint.getController().getPrimaryColor());
			if(paint.getController().getDrawer().getFillShapesWithSecondary()) {
				paint.getController().getDrawCanvas().getGraphicsContext2D().setFill(paint.getController().getSecondaryColor());
			} else {
				paint.getController().getDrawCanvas().getGraphicsContext2D().setFill(paint.getController().getPrimaryColor());
			}
			break;
		case IMAGE_CHANGED: //set the image as having been changed
			paint.getController().getPastHandler().addImage(paint.getImageHandler().getImage());
			break;
		case UNDO:
			if(paint.getController().getPastHandler().canUndo()) {
				paint.getController().getCurrentLayer().getGraphicsContext2D().drawImage(paint.getController().getPastHandler().undo(), 0, 0);
			}
			break;
		case REDO:
			if(paint.getController().getPastHandler().canRedo()) {
				paint.getController().getCurrentLayer().getGraphicsContext2D().drawImage(paint.getController().getPastHandler().redo(), 0, 0);
			}
			break;
		case CUT:
			paint.getController().getDrawer().cut();
			break;
		case PASTE:
			paint.getController().getDrawer().paste();
			break;
		case CROP: break;
	/*	case CROP:
			paint.getController().getDrawer().crop();	*/
		}
	}

}
