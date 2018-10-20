package ass2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MapSave {
	public MapSave() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * saves the map into a text file with the input.txt
	 * @param map
	 * @param fileName
	 */
	public void save(String fileName, Map map) {
		Map savedMap = map;
		try {
			FileOutputStream f = new FileOutputStream(new File(fileName));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(savedMap);
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error initializing stream");
		}

	}
	
	/**
	 * 
	 * @param fileName
	 * @return map read from file
	 */
	public Map load(String fileName) {
		Map loadedMap = null;
		try {
			FileInputStream fi = new FileInputStream(new File(fileName));
			ObjectInputStream oi = new ObjectInputStream(fi);
			loadedMap = (Map) oi.readObject();
			oi.close();
			fi.close();
			return loadedMap;
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}	
		return loadedMap;
	}
}
