package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main extends Application {
	Button mainButton;
	Button clearButton;
	TextField urlText;
	TextArea output;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		mainButton = new Button("Analyze Text");
		mainButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AnalyzeText();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		urlText = new TextField();
		urlText.setLayoutX(10);
		urlText.setLayoutY(10);
		urlText.setMinWidth(200);
		urlText.setText("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		
		mainButton.setLayoutX(220);
		mainButton.setLayoutY(10);
		
		clearButton = new Button("Clear Output");
		clearButton.setLayoutX(400);
		clearButton.setLayoutY(10);
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				output.setText("");
			}
		});
		
		output = new TextArea();
		output.setLayoutX(10);
		output.setLayoutY(50);
		
		AnchorPane root = new AnchorPane();
		root.getChildren().add(mainButton);
		root.getChildren().add(urlText);
		root.getChildren().add(output);
		root.getChildren().add(clearButton);
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setTitle("Text Word Counter");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public ArrayList<WordStat> AnalyzeTextFromPoemWebsite(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements paragraphs = doc.select("p");
		Map<String, Integer> frequency = new HashMap<>();

		for (Element p : paragraphs) {
			String[] words = p.text().split(" ");

			for (String word : words) {
				String lowecaseWords = word.toLowerCase();
				String sanitizedWords = lowecaseWords.replaceAll("[^a-zA-Z]", "");

				if (frequency.containsKey(sanitizedWords)) {
					frequency.put(sanitizedWords, frequency.get(sanitizedWords) + 1);
				} else {
					frequency.put(sanitizedWords, 1);
				}
			}
		}

		ArrayList<WordStat> stats = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
			WordStat ws = new WordStat();
			ws.word = entry.getKey();
			ws.count = entry.getValue();
			stats.add(ws);
		}

		Collections.sort(stats, Collections.reverseOrder());
		
		return stats;
	}
	
	public void AnalyzeText() throws IOException {	
		ArrayList<WordStat> stats = AnalyzeTextFromPoemWebsite(urlText.getText());

		for (int i = 0; i < 20; i++) {
			WordStat ws = stats.get(i);
			String printable = ws.getPrintable();
			output.setText(output.getText() + printable + "\n");
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}