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
		//image.filter(PImage.GRAY);
		AnswerSheet answers = new AnswerSheet();

		int topLeftCenterRow = 113;
		int topLeftCenterCol = 456;
		int verticalSpace = 37;
		int horizontalSpace = 37;
		for (int rowImage = topLeftCenterRow; rowImage < 280; rowImage += verticalSpace) {
			for (int colImage = topLeftCenterCol; colImage < 1000; colImage += horizontalSpace) {
				answers.addAnswer(determineBubble(topLeftCenterRow, topLeftCenterCol, horizontalSpace,
						verticalSpace, 5, image.pixels, image));
			}
		}
		return answers;
	}

	public String determineBubble(int r, int c, int width, int height, int numBubbles, int[] pixels, PImage image) {
		String bubbleAnswer = "";
		int boxWidth = width / numBubbles, darkest = 255, maxBubble = 5;
		for (int i = 0; i < numBubbles; i++) {
			int value = getSumValue(r, c + i * boxWidth, width, height, image);
			System.out.println("Value: " + value);
			if (value < darkest) {
				System.out.println("HI!");
				darkest = value;
				maxBubble = i;
				System.out.println("MaxBubble: " + i);
			}
		}
		if (maxBubble == 0)
			bubbleAnswer = "A";
		if (maxBubble == 1)
			bubbleAnswer = "B";
		if (maxBubble == 2)
			bubbleAnswer = "C";
		if (maxBubble == 3)
			bubbleAnswer = "D";
		if (maxBubble == 4)
			bubbleAnswer = "E";

		return bubbleAnswer;
	}

	public int getSumValue(int r, int c, int width, int height, PImage image) {
		int sum = 0;
		int counter = 0;
		int newcounter = 0;

		for (int i = 0; i < r + height; i++) {
			newcounter++;
			for (int j = 0; j < c + width; j++) {
				sum += getPixelAt(r + i, c + j, image);
				System.out.println(getPixelAt(r + i, c + j, image));
				counter++;

			}
		}

		System.out.println("SUM: " + sum);
		System.out.println("Counter: " + (counter));
		System.out.println("New Counter: " + newcounter);
		return sum / (counter);
	}

	public int getPixelAt(int row, int col, PImage image) {
		//image.loadPixels();

		int index = row * image.width + col;

		return image.pixels[index] & 255;
	}
}
