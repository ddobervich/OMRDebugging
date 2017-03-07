import java.util.ArrayList;

import com.sun.javafx.geom.Rectangle;

import processing.core.PImage;

/***
 * Class to perform image processing for optical mark reading
 * 
 */
public class OpticalMarkReader {

	/***
	 * Method to do optical mark reading on page image. Return an AnswerSheet
	 * object representing the page answers.
	 * 
	 * @param image
	 * @return
	 */
	public AnswerSheet processPageImage(PImage image) {
		image.filter(PImage.GRAY);
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf("/omrtest2.pdf");
		PImage currentImage;
		PImage answerKey;
		int currentImageIndex = 0;

		answerKey = images.get(0);
		currentImage = images.get(0);
		
		AnswerSheet answers = new AnswerSheet();
		
		int topLeftCenterRow = 133;
		int topLeftCenterCol = 476;
		int verticalSpace = 40;
		int horizontalSpace = 40;

		// for (int rowImage = topLeftCenterRow; rowImage < currentImage.height; rowImage += verticalSpace) {
			//for (int colImage = topLeftCenterCol; colImage < currentImage.width; colImage += horizontalSpace) {
				answers.addAnswer(determineBubble(topLeftCenterRow, topLeftCenterCol, horizontalSpace, verticalSpace, 5, image.pixels, image));
		//	}
		// }
		return answers;
	}

	public String determineBubble(int r, int c, int width, int height, int numBubbles, int[] pixels, PImage image) {
		String bubbleAnswer = "";
		int boxWidth = width / numBubbles, max = 255, maxBubble = 255;
		for (int i = 0; i < numBubbles; i++) {
			int value = getSumValue(r, c + i * boxWidth, width, height, image);
			if (value < max) {
				max = value;
				maxBubble = i;
			}
		}
		if (maxBubble == 0) bubbleAnswer = "A";
		if (maxBubble == 1) bubbleAnswer = "B";
		if (maxBubble == 2) bubbleAnswer = "C";
		if (maxBubble == 3) bubbleAnswer = "D";
		if (maxBubble == 4) bubbleAnswer = "E";
		
		return bubbleAnswer;
	}

	public int getSumValue(int r, int c, int width, int height, PImage image) {
		int sum = 0;
	
		for (int i = 0; i < r - height; i++) {
			for (int j = 0; j < c - width; j++) {
				sum += getPixelAt(r + i, c + j, image);
				System.out.println(sum);
			}
		}
		return sum;
	}

	public int getPixelAt(int row, int col, PImage image) {
		image.loadPixels();

		int index = row * image.width + col;

		return image.pixels[index] & 255;
	}
}
