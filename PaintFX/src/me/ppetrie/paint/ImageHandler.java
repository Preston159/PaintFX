package me.ppetrie.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Transform;
import javafx.util.Pair;

public class ImageHandler {
	
	private Paint paint;
	
	/**
	 * The file of the currently-open image
	 */
	private File currentImage;
	/**
	 * Whether or not the image has been changed from its last save
	 */
	private boolean imageChanged = false;
	
	
	public ImageHandler(Paint paint) {
		this.paint = paint;
	}
	
	
	/**
	 * Loads an image from a File
	 * @param f	the File from which to load the image
	 */
	public void setImage(File f) {
		Image image;
		if(f != null) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			image = new Image(fis);
			paint.setFileName(f.getName());
		} else {
			Dialog<Pair<String, String>> sizeDialog = new Dialog<>();
			sizeDialog.setTitle("Image dimensions");
			sizeDialog.setHeaderText("Please enter the image dimensions");
			sizeDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
			GridPane gridPane = new GridPane();
			gridPane.setHgap(10);
			gridPane.setVgap(10);
			gridPane.setPadding(new Insets(20, 150, 10, 10));
			TextField width = new TextField("400");
			width.setPromptText("Width");
			TextField height = new TextField("400");
			height.setPromptText("Height");
			gridPane.add(new Label("Width:"), 0, 0);
			gridPane.add(width, 1, 0);
			gridPane.add(new Label("Height:"), 0, 1);
			gridPane.add(height, 1, 1);
			sizeDialog.getDialogPane().setContent(gridPane);
			sizeDialog.setResultConverter(dialogButton -> {
				return new Pair<>(width.getText(), height.getText());
			});
			Optional<Pair<String, String>> resultOpt = sizeDialog.showAndWait();
			Pair<String, String> result = resultOpt.get();
			BufferedImage bi;
			try {
				bi = new BufferedImage(Integer.valueOf(result.getKey()),
						Integer.valueOf(result.getValue()), BufferedImage.TYPE_INT_RGB);
			} catch(NumberFormatException nfe) {
				bi = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
			}
			Graphics graphics = bi.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
			image = SwingFXUtils.toFXImage(bi, null);
			paint.setFileName("New Image");
		}
		
		paint.getController().getCurrentLayer().setHeight(image.getHeight());
		paint.getController().getCurrentLayer().setWidth(image.getWidth());
		paint.getController().getCurrentLayer().getGraphicsContext2D().drawImage(image, 0, 0);
		
		paint.getController().getDrawCanvas().setHeight(image.getHeight());
		paint.getController().getDrawCanvas().setWidth(image.getWidth());
		
		currentImage = f;
		setImageChanged(false);
		paint.getController().getPastHandler().reset(image);
	}
	
	/**
	 * @return	the currently-loaded Image
	 */
	public Image getImage() {
		int height = (int) paint.getController().getCurrentLayer().getHeight();
		int width = (int) paint.getController().getCurrentLayer().getWidth();
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(paint.getController().getInverseScale(), paint.getController().getInverseScale()));
		return paint.getController().getCurrentLayer().snapshot(params, new WritableImage(width, height));
	}
	
	/**
	 * @return	the File from which the current image was loaded
	 */
	public File getCurrentImageFile() {
		return this.currentImage;
	}
	
	/**
	 * Sets the File of the image
	 * @param newImage	the File which should be overwritten on save
	 */
	public void setCurrentImageFile(File newImage) {
		this.currentImage = newImage;
		setImageChanged(false);
	}
	
	/**
	 * @param changed	whether the image has been changed since last save
	 */
	public void setImageChanged(boolean changed) {
		imageChanged = changed;
		paint.setUnsaved(changed);
	}
	
	/**
	 * @return	true if the image has been changed since last save, false otherwise
	 */
	public boolean getImageChanged() {
		return imageChanged;
	}
	
}
