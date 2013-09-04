package b0nde.asciiconverter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args){
		
		Path path = Paths.get("./files/ASCII.txt");
		try {
			BufferedReader reader = new BufferedReader(Files.newBufferedReader(path, StandardCharsets.UTF_8));
			ArrayList<String> lines = new ArrayList<String>();
			
			String line = "";
			
			while((line = reader.readLine()) != null){
				
				lines.add(line);
			}
			
			reader.close();
			
			BufferedImage image = new BufferedImage(lines.get(1).length(), lines.size(), BufferedImage.TYPE_INT_RGB);
			int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			
			for(int y = 0; y < lines.size(); y++){
				
				String asciiLine = lines.get(y);
				
				for(int x = 0; x < lines.get(1).length(); x++){
					
					int value = ((int)asciiLine.charAt(x));
					int r = value << 16;
					int b = value << 8;
					int g = value;
					
					pixels[x + y * image.getWidth()] = r | b | g;
				}
			}
			
			FileOutputStream stream = new FileOutputStream("./image.png");			
			ImageIO.write(image, "PNG", stream);
			stream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}