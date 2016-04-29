package controllers.lightning;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import boundary.JBlockPanel;
import boundary.lightning.LightningLevelView;
import controllers.BlockController;
import controllers.BoardController;
import controllers.BullpenControler;
import controllers.IController;
import controllers.ILevelController;
import controllers.MainController;
import entities.EmptyBlock;
import entities.IBlock;
import entities.Model;
import entities.PuzzleLevel;
import entities.LightningLevel;
import entities.Tile;
import move.NonOverlayMove;
import move.OverlayMove;

public class LightningLevelController implements IController, ILevelController{
	private LightningLevelView lightningLevelView;
	private MainController mainController;
	private IController back;
	private Model model;
	int levelNum;
	
	BullpenControler bullpenController;
	BoardController boardController;
	BlockController blockController;
	JPanel renderPanel;
	LinkedList<JBlockPanel> currentBlockPanelList;
	
	public LightningLevelController(MainController mainController, IController back, Model model, int levelNum) {
		this.mainController = mainController;
		this.back = back;
		this.model = model;
		this.levelNum = levelNum;
		
		lightningLevelView = new LightningLevelView(model.getLevel(levelNum));
		blockController = new BlockController(new EmptyBlock(), this);
		bullpenController = new BullpenControler(model.getLevel(levelNum).getBullpen(), blockController);
		boardController = new BoardController(model.getLevel(levelNum).getBoard());
		currentBlockPanelList = null;
		
	}
	

	@Override
	public JPanel getRenderedView() {
		// Render the main view
		renderPanel = lightningLevelView.render();
		
		//Attach button
		lightningLevelView.getBackButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				backButtonClicked();
			}	
		});
		
		// Render the components to the 0th layer
		lightningLevelView.getLayeredPane().add(boardController.render(), new Integer(0), 0);
		lightningLevelView.getLayeredPane().add(bullpenController.render(), new Integer(0), 0);
		
		return renderPanel;
		
	}
		
	
	private void backButtonClicked() {
		model.reload();
		mainController.requestSwap(back);
	}

	public void piecePressed(JBlockPanel jBlockPanel) {
		currentBlockPanelList = bullpenController.pop(jBlockPanel);
		
		for(JBlockPanel item: currentBlockPanelList){
			try{
				renderPanel.add(item, new Integer(1), 0);
			}catch(Exception e){
				e.printStackTrace();	
			}
		}
	}


	public void pieceReleased(JBlockPanel jBlockPanel) {
		// Lists for the move
		LinkedList<Tile> tl = new LinkedList<>();
		LinkedList<IBlock> bl= new LinkedList<>();

		
		// Populating the lists
		for(JBlockPanel jbp: currentBlockPanelList){
			try{
				// The tile underneath the JBlockPanel is found and added to the same index of the lists
				Tile temp = boardController.getTileAtPoint(new Point(16+jbp.getLocation().x, 16+jbp.getLocation().y));
				bl.add(jbp.getBlock());
				tl.add(temp);
			} catch (Exception e){
			// If the bounds are out of board we will receive an exception and it is handled here
			// The solution is to re-render without any moves whatsoever.
				mainController.requestSwap(this);
				return;
			}
		}		
				
		// If we get here we check for the validity of the move in doMove() and we act according to it
		if(new OverlayMove(bl, tl).doMove()){
		// If the processor gets here this means that the move was valid
			try {
				// We get the level and remove the piece from the bullpen
				// Can be different for other level types
				model.getLevel(levelNum).getBullpen().removePiece(bl.getFirst().getPiece());
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		// Update the moves left
		LightningLevel lvl = (LightningLevel) model.getLevel(levelNum);
		lvl.updateStars();
		
		// finally we re-render
		mainController.requestSwap(this);
	}
}

