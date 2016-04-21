package application;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import boundary.WindowManager;

import controllers.GameMainController;
import entities.Model;
import entities.Piece;
import entities.Achievement;
import entities.Board;
import entities.Bullpen;
import entities.EmptyBlock;
import entities.Level;
import entities.Tile;


public class Application {
	private Model model;
	
	public Application(){
		initialize();
	}
	
	public Model getModel(){
		return model;
	}
	
	
	public void initialize() {
		initializeModel();
		
	}
	
	public void initializeModel(){
		
		ArrayList<Tile> mockTileRow = (new ArrayList<Tile>());
		Tile mockTile = new Tile(new EmptyBlock(), null, true);
		Tile mockTile2 = new Tile(new EmptyBlock(), null, false);
		
		boolean test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile2);
		test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile2);
		test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile2);
		test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile);
		test = mockTileRow.add(mockTile2);
		test = mockTileRow.add(mockTile);
		
		ArrayList<ArrayList<Tile>> mockTiles = (new ArrayList<ArrayList<Tile>>());
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		test = mockTiles.add(mockTileRow);
		
		ArrayList<Piece> mockArrayPieces = (new ArrayList<Piece>());
		Board mockBoard = new Board(mockTiles);
		Bullpen mockBullpen = new Bullpen(mockArrayPieces);
		Level mockLevel = new Level(true, 16 ,mockBullpen, mockBoard, true);
		
		ArrayList<Level> mockLevels = new ArrayList<Level>();
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		mockLevels.add(mockLevel);
		
		
		BufferedImage mock_img = null;
		try {
			// load relative image
			URL img = boundary.AchievementView.class.getResource("/img/888267.jpg");
			mock_img = ImageIO.read(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image mock_img_r = mock_img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		
		Achievement mockAchievement = new Achievement("yolo",mock_img_r, true);
		ArrayList<Achievement> mockAchievements = new ArrayList<Achievement>();
		mockAchievements.add(mockAchievement);
		
		
		Model mockModel = new Model("Kabasuji", mockAchievements, mockLevels);
		
		this.model = mockModel;
	}

	public static void main(String[] args) {
		WindowManager wmg = new WindowManager(200, 200, 640, 535);
		wmg.link(new GameMainController(new Application()));
	}

}
