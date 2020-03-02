import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * This is the main application.  Note that while it is a JavaFX application it doesn't
 * actually "show" the main scene.  We just need the application for the fileChooser.
 */
public class HouseBuilder extends Application{
	
	HouseFactory furnitureFactory = new FurnitureFactory();
	HouseFactory houseAreaFactory = new HouseAreaFactory();
	HouseArea house;
	/**
	 * Manually construct a house
	 */
	public void buildHouse(){
		HouseEntity block1 = furnitureFactory.createHouse("Sink");
		HouseEntity block2 = furnitureFactory.createHouse("Counter");
		HouseEntity block3 = furnitureFactory.createHouse("Bed");
		HouseEntity block4 = furnitureFactory.createHouse("Dresser");
		HouseEntity block5 = furnitureFactory.createHouse("Bathtub");
                        
           //Initialize composite structures
		HouseEntity structure = houseAreaFactory.createHouse("Kitchen");
		HouseEntity structure1 = houseAreaFactory.createHouse("Bedroom");
		HouseEntity structure2 = houseAreaFactory.createHouse("Bathroom");
		HouseEntity structure3 = houseAreaFactory.createHouse("Downstairs");
		HouseEntity structure4 = houseAreaFactory.createHouse("Upstairs");
        house = (HouseArea) houseAreaFactory.buildHouse("House");
        
        
        
        
        
        
        
        
        //Build the house
        house.add(structure3);
        house.add(structure4);
        
        structure4.add(structure1);
        structure4.add(structure2);
        
        structure3.add(structure);
        
        structure.add(block1);
        structure.add(block2);
        structure1.add(block3);
        structure1.add(block4);
        structure2.add(block5);     
	}
	
	/**
	 * Save using serialization
	 * @param fileName
	 */
	public void save(String fileName){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( new FileOutputStream(fileName));
			oos.writeObject(house);  //serializing employee
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	
	public void countHouseContents(){
		System.out.println("House includes: " + house.countContents() + " areas and/or furniture items.");
	}
	
	public void printHouseSpecs(){
		house.listHouseSpecs(0);
	}
	
	public HouseArea getHouse(){
		return house;
	}
	
	
	/**
	 * Restore from serialized form
	 * @param fileName
	 */
	public void restore(String fileName){
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream( new FileInputStream(fileName));
			house = (HouseArea) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public String getFileName(Stage primaryStage){
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setInitialDirectory(new File("/home/dj/Nextcloud/Computer_Science/Eclipse/eclipse-workspace/CSCI_513_Lab4_Team-Johnson/src"));  // This is optional
		 fileChooser.setTitle("Serialization File");
		 File file = fileChooser.showOpenDialog(primaryStage);
		 return file.getAbsolutePath();
	}
	
	 public static void main(String[] args) {
		 launch(args);      
	 }

	@Override
	public void start(Stage primaryStage) throws Exception {
		  HouseBuilder houseBuilder = new HouseBuilder();
	      houseBuilder.buildHouse();
	      houseBuilder.save("/home/dj/Nextcloud/Computer_Science/Eclipse/eclipse-workspace/CSCI_513_Lab4_Team-Johnson/src/" + "myHouse.ser");
	      String filename = houseBuilder.getFileName(primaryStage);
	      houseBuilder.restore(filename);
	      houseBuilder.printHouseSpecs();
	      houseBuilder.countHouseContents();		
	}      	       
}
