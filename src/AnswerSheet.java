import java.util.ArrayList;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	ArrayList<String> answers = new ArrayList<String>();
	
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public String getAnswerNumber(int number) {
		return answers.get(number);
	}
	public void addAnswer(String answer) {
		answers.add(answer);
	}
}
