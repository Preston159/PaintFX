package com.ppetrie.paintfx.events;

import com.ppetrie.paintfx.Paint;
import com.ppetrie.paintfx.util.FixedStack;

import javafx.scene.image.Image;

public class PastHandler {
	
	/**
	 * The main class of the program
	 */
	private Paint paint;
	
	/**
	 * The number of steps forward/backward which will be saved
	 */
	private static final int NUM_STEPS = 15;
	
	/**
	 * The stack containing past images for undo
	 */
	private FixedStack<Image> past = new FixedStack<Image>(NUM_STEPS);
	/**
	 * The stack containing future images for redo
	 */
	private FixedStack<Image> future = new FixedStack<Image>(NUM_STEPS);
	
	/**
	 * Create a PastHandler
	 * @param paint	the main class of the program
	 */
	public PastHandler(Paint paint) {
		this.paint = paint;
	}
	
	/**
	 * Adds a new image to the past
	 * @param image	the new image
	 */
	public void addImage(Image image) {
		past.push(image);
		future.clear();
		updateButtons();
	}
	
	/**
	 * Gets the image after undoing
	 * @return	the image after undoing
	 */
	public Image undo() {
		future.push(past.pop());
		Image undoImg = past.peek();
		updateButtons();
		return undoImg;
	}
	
	/**
	 * Checks whether an undo can be performed
	 * @return	true if an undo can be performed, false otherwise
	 */
	public boolean canUndo() {
		return past.size() > 1;
	}
	
	/**
	 * Get the image after redoing
	 * @return	the image after redoing
	 */
	public Image redo() {
		Image redoImg = future.pop();
		past.push(redoImg);
		updateButtons();
		return redoImg;
	}
	
	/**
	 * Checks whether a redo can be performed
	 * @return	true if a redo can be performed, false otherwise
	 */
	public boolean canRedo() {
		return future.size() > 0;
	}
	
	/**
	 * Reset the past handler, such as when a new image is loaded
	 * @param image	the starting image
	 */
	public void reset(Image image) {
		past.clear();
		future.clear();
		past.push(image);
		updateButtons();
	}
	
	/**
	 * Update the state of the undo and redo buttons
	 */
	private void updateButtons() {
		if(canRedo()) {
			paint.getController().setRedoEnabled(true);
		} else {
			paint.getController().setRedoEnabled(false);
		}
		if(canUndo()) {
			paint.getController().setUndoEnabled(true);
		} else {
			paint.getController().setUndoEnabled(false);
		}
	}
	
	/**
	 * Returns whether the current image has been changed
	 * @return	true if the image has been changed, false otherwise
	 */
	public boolean hasBeenChanged() {
		return past.size() > 1;
	}
	
}
