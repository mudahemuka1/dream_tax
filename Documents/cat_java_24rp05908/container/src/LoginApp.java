import java.awt.*;
import java.awt.event.*;

public class LoginApp extends Frame {
    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    private Label messageLabel;
    private String storedUsername = "admin";
    private String storedPassword = "password123";

    public LoginApp() {
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        setTitle("Login Application");
        setLayout(new BorderLayout());
        setSize(500, 400);
        setBackground(Color.WHITE);

        Panel mainContainer = new Panel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        Panel headerPanel = new Panel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setPreferredSize(new Dimension(500, 30));

        Label headerLabel = new Label("AWT Practice");
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(headerLabel);

        Panel titlePanel = new Panel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.WHITE);

        Label mainTitle = new Label("SIMPLE JAVA AWT LAYOUT");
        mainTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mainTitle.setForeground(Color.BLUE);
        titlePanel.add(mainTitle);

        Panel loginTitlePanel = new Panel();
        loginTitlePanel.setLayout(new FlowLayout());
        loginTitlePanel.setBackground(Color.WHITE);

        Label loginTitle = new Label("LOGIN PAGE");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 16));
        loginTitle.setForeground(Color.DARK_GRAY);
        loginTitlePanel.add(loginTitle);

        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        usernameField = new TextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new TextField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setEchoChar('*');

        loginButton = new Button("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(Color.GREEN);

        messageLabel = new Label("", Label.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(Color.RED);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(new Label(""));
        formPanel.add(loginButton);
        formPanel.add(new Label(""));
        formPanel.add(messageLabel);

        Panel centerPanel = new Panel();
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(titlePanel, BorderLayout.NORTH);
        centerPanel.add(loginTitlePanel, BorderLayout.CENTER);
        centerPanel.add(formPanel, BorderLayout.SOUTH);

        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(centerPanel, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
    }

    private void setupEvents() {
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password");
            return;
        }

        if (username.equals(storedUsername) && password.equals(storedPassword)) {
            messageLabel.setText("Welcome, " + username + "!");
            messageLabel.setForeground(Color.GREEN);
        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
            messageLabel.setForeground(Color.RED);
            usernameField.setText("");
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }

    public static void main(String[] args) {
        LoginApp loginApp = new LoginApp();
        loginApp.setVisible(true);
    }
}