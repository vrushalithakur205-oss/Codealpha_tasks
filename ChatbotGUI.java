import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ChatBotGUI extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private HashMap<String, String> knowledgeBase;

    public ChatBotGUI() {
        setTitle("AI Chatbot");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        trainBot();

        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput());
    }

    private void trainBot() {
        knowledgeBase = new HashMap<>();

        knowledgeBase.put("hello", "Hello! How can I help you?");
        knowledgeBase.put("hi", "Hi there 😊");
        knowledgeBase.put("how are you", "I'm an AI chatbot, always ready!");
        knowledgeBase.put("what is ai", "AI stands for Artificial Intelligence.");
        knowledgeBase.put("what is java", "Java is an object-oriented programming language.");
        knowledgeBase.put("your name", "I am a Java AI Chatbot.");
        knowledgeBase.put("bye", "Goodbye! Have a nice day 👋");
    }

    private void processInput() {
        String userText = inputField.getText().toLowerCase().trim();
        chatArea.append("You: " + userText + "\n");

        String response = getResponse(userText);
        chatArea.append("Bot: " + response + "\n\n");

        inputField.setText("");
    }

    private String getResponse(String input) {
        for (String key : knowledgeBase.keySet()) {
            if (input.contains(key)) {
                return knowledgeBase.get(key);
            }
        }
        return "Sorry, I didn't understand that.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatBotGUI().setVisible(true);
        });
    }
}
