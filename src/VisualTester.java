import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class VisualTester extends PApplet {
	ArrayList<PImage> images;
	PImage current_image;
	int currentImageIndex = 0;
	int w = 1200;
	int h = 900;

	
	int topLeftCenterRow = 113;
	int topLeftCenterCol = 456;
	int verticalSpace = 37;
	int horizontalSpace = 37;
	
	public void setup() {
		size(w, h);
		images = PDFHelper.getPImagesFromPdf("/omrtest2.pdf");
	}

	public void draw() {
		background(255);
		if (images.size() > 0) {
			current_image = images.get(currentImageIndex);
			
			image(current_image, 0, 0);			// display image i
			fill(0);
			for (int rowImage = topLeftCenterRow; rowImage < 800; rowImage += verticalSpace) {
				for (int colImage = topLeftCenterCol; colImage < 1000; colImage += horizontalSpace) {
					fill(0);
					noFill();
					rect(rowImage, colImage, verticalSpace, horizontalSpace);
				}
			}
			text(mouseX + " " + mouseY, 30, 30);
		}
	}

	public void mouseReleased() {
		currentImageIndex = (currentImageIndex + 1) % images.size();			// increment current image
	}
}
