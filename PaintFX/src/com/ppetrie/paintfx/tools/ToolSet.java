package com.ppetrie.paintfx.tools;

import java.util.ArrayList;

/**
 * Stores an instance of each tool
 */
public class ToolSet {
	
	public static DropperTool DROPPER;
	public static EraserTool ERASER;
	public static LineTool LINE;
	public static MoveTool MOVE;
	public static PenTool PEN;
	public static ShapeTool SHAPE;
	public static TextTool TEXT;
	
	/**
	 * The set of all tools
	 */
	public static ArrayList<Tool> all = new ArrayList<Tool>(7);
	
	/**
	 * @return	an ArrayList containing all the tools
	 */
	public static ArrayList<Tool> getAll() {
		if(all.isEmpty()) {
			all.add(DROPPER);
			all.add(ERASER);
			all.add(LINE);
			all.add(MOVE);
			all.add(PEN);
			all.add(SHAPE);
			all.add(TEXT);
		}
		return all;
	}
	

}
