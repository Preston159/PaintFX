package me.ppetrie.paint.tools;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventType;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;
import me.ppetrie.paint.PaintController;
import me.ppetrie.paint.dialogs.TextDialog;
import me.ppetrie.paint.events.Drawer.Tools;

public class TextTool extends Tool {
	
	private PaintController controller;
	
	private TextDialog textDialog;
	
	public TextTool(Button button, CheckMenuItem menuItem, PaintController controller) {
		super(button, menuItem);
		this.controller = controller;
		textDialog = new TextDialog(controller);
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		
	}
	
	public void handleText() {
		String result = textDialog.showAndWait();
		
		double height = controller.getCurrentLayer().getHeight();
		double width = controller.getCurrentLayer().getWidth();
		
		if((int) height <= 0 || (int) width <= 0) {
			return;
		}
		
		GraphicsContext gc = controller.getDrawCanvas().getGraphicsContext2D();
		
		if(ToolSet.SHAPE.getFillShapes()) {
			gc.fillText(result, 0, gc.getFont().getSize() / (3d / 2) + 10);
		}
		gc.strokeText(result, 0, gc.getFont().getSize() / (3d / 2) + 10);
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(controller.getInverseScale(), controller.getInverseScale()));
		params.setFill(javafx.scene.paint.Color.TRANSPARENT);
		Image drawn = controller.getDrawCanvas().snapshot(params, new WritableImage((int) width, (int) height));
		BufferedImage bi = SwingFXUtils.fromFXImage(drawn, null);
		controller.getDrawer().clearDrawCanvas();
		ToolSet.MOVE.setSelection(bi);
		
		controller.getDrawer().setTool(Tools.MOVE);
		ToolSet.MOVE.setAwaitingPlace();
	}

}
