package me.ppetrie.paint;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import me.ppetrie.paint.events.Drawer;
import me.ppetrie.paint.events.EventListener;
import me.ppetrie.paint.events.PaintEventType;
import me.ppetrie.paint.events.PastHandler;
import me.ppetrie.paint.tools.*;
import me.ppetrie.paint.events.Drawer.Tools;

public class PaintController {
	
	private Paint paint = null;
	
	private EventListener listener = null;
	private Drawer drawer = null;
	private PastHandler pastHandler = null;
	
	private double scale = 1;
	
	//
	
	int currentLayer = 0;
	private ArrayList<Canvas> layers = new ArrayList<Canvas>();
	
	@FXML private CheckMenuItem fillToggle;
	@FXML private CheckMenuItem fillSecToggle;
	@FXML private Canvas canvas;
	@FXML private Canvas drawCanvas;
	@FXML private ColorPicker primaryColor;
	@FXML private ColorPicker secondaryColor;
	
	@FXML private Button lineButton;
	@FXML private Button penButton;
	@FXML private Button eraserButton;
	@FXML private Button moveButton;
	@FXML private Button shapeButton;
	@FXML private Button dropperButton;
	@FXML private Button textButton;
	@FXML private CheckMenuItem lineCheck;
	@FXML private CheckMenuItem penCheck;
	@FXML private CheckMenuItem eraserCheck;
	@FXML private CheckMenuItem moveCheck;
	@FXML private CheckMenuItem shapeCheck;
	@FXML private CheckMenuItem dropperCheck;
	@FXML private CheckMenuItem textCheck;
	
	@FXML private ComboBox<String> widthBox;
	@FXML private ComboBox<String> scaleBox;
	@FXML private MenuItem undoButton;
	@FXML private MenuItem redoButton;
	
	/**
	 * @return	the canvas belonging to the layer currently being edited
	 */
	public Canvas getCurrentLayer() {
		return layers.get(currentLayer);
	}
	
	/**
	 * @return	an ArrayList containing the canvas for each layer
	 */
	public ArrayList<Canvas> getAllLayers() {
		return layers;
	}
	
	/**
	 * @return	the draw canvas
	 */
	public Canvas getDrawCanvas() {
		return this.drawCanvas;
	}
	
	/**
	 * @return	the current primary color
	 */
	public Color getPrimaryColor() {
		return primaryColor.getValue();
	}
	
	/**
	 * @return	the primary color picker
	 */
	public ColorPicker getPrimaryColorPicker() {
		return primaryColor;
	}
	
	/**
	 * @return	the current secondary color
	 */
	public Color getSecondaryColor() {
		return secondaryColor.getValue();
	}
	
	/**
	 * @return	the secondary color picker
	 */
	public ColorPicker getSecondaryColorPicker() {
		return secondaryColor;
	}
	
	/**
	 * @return	the event listener
	 */
	public EventListener getEventListener() {
		return this.listener;
	}
	
	/**
	 * @return	the past handler
	 */
	public PastHandler getPastHandler() {
		return this.pastHandler;
	}
	
	/**
	 * Initiate variables, listeners, tools, etc.
	 * @param paint	the main JavaFX class of the program
	 */
	public void init(Paint paint) {
		this.paint = paint;
		listener = new EventListener(paint);
		drawer = new Drawer(paint, this);
		pastHandler = new PastHandler(paint);
		
		drawCanvas.addEventHandler(MouseEvent.ANY, drawer);
		primaryColor.setValue(Color.BLACK);
		listener.handleType(PaintEventType.UPDATE_COLORS);
		
		scaleBox.getSelectionModel().select("100%");
		
		scaleBox.valueProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
				newVal = newVal.replace("%", "");
				try {
					setScale(Integer.valueOf(newVal) / 100d);
					scaleBox.getSelectionModel().select(newVal + "%");
				} catch(NumberFormatException nfe) {
					scaleBox.getSelectionModel().select(oldVal);
				}
			}
			
		});
		
		widthBox.valueProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
				try {
					drawer.setWidth(Integer.valueOf(newVal));
				} catch(NumberFormatException nfe) {
					widthBox.getSelectionModel().select(oldVal);
				}
			}
			
		});
		
		widthBox.getSelectionModel().select(1);
		
		layers.add(canvas);
		
		
		ToolSet.DROPPER = new DropperTool(dropperButton, dropperCheck, paint);
		ToolSet.ERASER = new EraserTool(eraserButton, eraserCheck);
		ToolSet.LINE = new LineTool(lineButton, lineCheck);
		ToolSet.MOVE = new MoveTool(moveButton, moveCheck, paint);
		ToolSet.PEN = new PenTool(penButton, penCheck);
		ToolSet.SHAPE = new ShapeTool(shapeButton, shapeCheck, paint);
		ToolSet.TEXT = new TextTool(textButton, textCheck, this);
		
	}
	
	/**
	 * Enables or disables the undo button
	 * @param enabled	whether the undo button should be enabled
	 */
	public void setUndoEnabled(boolean enabled) {
		undoButton.setDisable(!enabled);
	}
	
	/**
	 * Enables or disables the redo button
	 * @param enabled	whether the redo button should be enabled
	 */
	public void setRedoEnabled(boolean enabled) {
		redoButton.setDisable(!enabled);
	}
	
	/**
	 * Changes the scale of the canvases
	 * @param scale	the new scale
	 */
	public void setScale(double scale) {
		this.scale = scale;
		for(Canvas c : layers) {
			c.setScaleX(scale);
			c.setScaleY(scale);
		}
		drawCanvas.setScaleX(scale);
		drawCanvas.setScaleY(scale);
	}
	
	/**
	 * @return	the current scale of the canvases
	 */
	public double getScale() {
		return this.scale;
	}
	
	/**
	 * @return	the inverse of the current scale of the canvases
	 */
	public double getInverseScale() {
		return 1 / this.scale;
	}
	
	/**
	 * @return the main Drawer object
	 */
	public Drawer getDrawer() {
		return this.drawer;
	}
	
	@FXML protected void handleNew() {
		listener.handleType(PaintEventType.NEW);
	}
	
	@FXML protected void handleOpen() {
		listener.handleType(PaintEventType.OPEN);
	}
	
	@FXML protected void handleSave() {
		listener.handleType(PaintEventType.SAVE);
	}
	
	@FXML protected void handleSaveAs() {
		listener.handleType(PaintEventType.SAVE_AS);
	}
	
	@FXML protected void handleExit() {
		listener.handleType(PaintEventType.EXIT);
	}
	
	@FXML protected void handleFillToggle() {
		drawer.setFillShapes(fillToggle.isSelected());
	}
	
	@FXML protected void handleFillSecToggle() {
		drawer.setFillShapesWithSecondary(fillSecToggle.isSelected());
		listener.handleType(PaintEventType.UPDATE_COLORS);
	}
	
	@FXML protected void handleColorChange() {
		listener.handleType(PaintEventType.UPDATE_COLORS);
	}
	
	@FXML protected void handleColorSwap() {
		listener.handleType(PaintEventType.SWAP_COLORS);
	}
	
	@FXML protected void handleToolChange(ActionEvent event) {
		if(!(event.getSource() instanceof EventTarget)) {
			return;
		}
		for(Tool tool : ToolSet.getAll()) {
			tool.getMenuItem().setSelected(false);
			tool.getButton().getStyleClass().remove("selected");
		}
		EventTarget source = (EventTarget) event.getSource();
		if(ToolSet.LINE.matches(source)) {
			drawer.setTool(Tools.LINE);
			ToolSet.LINE.getMenuItem().setSelected(true);
			ToolSet.LINE.getButton().getStyleClass().add("selected");
		} else if(ToolSet.PEN.matches(source)) {
			drawer.setTool(Tools.PEN);
			ToolSet.PEN.getMenuItem().setSelected(true);
			ToolSet.PEN.getButton().getStyleClass().add("selected");
		} else if(ToolSet.ERASER.matches(source)) {
			drawer.setTool(Tools.ERASER);
			ToolSet.ERASER.getMenuItem().setSelected(true);
			ToolSet.ERASER.getButton().getStyleClass().add("selected");
		} else if(ToolSet.MOVE.matches(source)) {
			drawer.setTool(Tools.MOVE);
			ToolSet.MOVE.getMenuItem().setSelected(true);
			ToolSet.MOVE.getButton().getStyleClass().add("selected");
		} else if(ToolSet.SHAPE.matches(source)) {
			drawer.setTool(Tools.SHAPE);
			ToolSet.SHAPE.getMenuItem().setSelected(true);
			ToolSet.SHAPE.getButton().getStyleClass().add("selected");
		} else if(ToolSet.DROPPER.matches(source)) {
			drawer.setTool(Tools.DROPPER);
			ToolSet.DROPPER.getMenuItem().setSelected(true);
			ToolSet.DROPPER.getButton().getStyleClass().add("selected");
		} else if(ToolSet.TEXT.matches(source)) {
			drawer.setTool(Tools.TEXT);
			ToolSet.TEXT.getMenuItem().setSelected(true);
			ToolSet.TEXT.getButton().getStyleClass().add("selected");
		}
		/*else if(event.getSource().equals(fillButton)) {
			drawer.setTool(Tools.FILL);
		}	*/
	}
	
	@FXML protected void handleUndo() {
		listener.handleType(PaintEventType.UNDO);
	}
	
	@FXML protected void handleRedo() {
		listener.handleType(PaintEventType.REDO);
	}
	
	@FXML protected void handleCut() {
		listener.handleType(PaintEventType.CUT);
	}
	
	@FXML protected void handlePaste() {
		listener.handleType(PaintEventType.PASTE);
	}
	
	@FXML protected void handleCrop() {
		listener.handleType(PaintEventType.CROP);
	}
	
}
