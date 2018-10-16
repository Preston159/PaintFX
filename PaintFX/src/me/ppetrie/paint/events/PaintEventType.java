package me.ppetrie.paint.events;

public enum PaintEventType {
	
	/**
	 * Open image
	 */
	OPEN,
	/**
	 * Create new image
	 */
	NEW,
	/**
	 * Save current image
	 */
	SAVE,
	/**
	 * Save current image as different/new file
	 */
	SAVE_AS,
	/**
	 * Exit program
	 */
	EXIT,
	/**
	 * Update selected colors
	 */
	 UPDATE_COLORS,
	 /**
	  * Swap primary and secondary colors
	  */
	 SWAP_COLORS,
	 /**
	  * Set the image as having been changed
	  */
	 IMAGE_CHANGED,
	 /**
	  * Undo last edit
	  */
	 UNDO,
	 /**
	  * Redo last undo
	  */
	 REDO,
	 /**
	  * Cut the selected portion of the image to the system clipboard
	  */
	 CUT,
	 /**
	  * Paste the image in the system clipboard
	  */
	 PASTE,
	 /**
	  * Crop to the selected portion of the image
	  */
	 CROP
	
}
