import java.awt.*;
import java.awt.event.*;

class AWTMenuApp extends Frame implements ActionListener {

    private CardLayout cardLayout = new CardLayout();
    private Panel contentPanel = new Panel();

    private static final String LOGIN_CARD = "Login";
    private static final String STUDENT_CARD = "Student";
    private static final String ADMIN_CARD = "Admin";

    public AWTMenuApp() {
        super("AWT MENU PRACTICE");
        setSize(400, 300);

        contentPanel.setLayout(cardLayout);
        setupMenuBar();

        contentPanel.add(createLoginPanel(), LOGIN_CARD);
        contentPanel.add(createStudentPanel(), STUDENT_CARD);
        contentPanel.add(createAdminPanel(), ADMIN_CARD);

        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, LOGIN_CARD);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private void setupMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu pagesMenu = new Menu("Pages");

        MenuItem loginItem = new MenuItem("Login");
        MenuItem studentItem = new MenuItem("Student");
        MenuItem adminItem = new MenuItem("Admin");

        loginItem.addActionListener(this);
        studentItem.addActionListener(this);
        adminItem.addActionListener(this);

        pagesMenu.add(loginItem);
        pagesMenu.add(studentItem);
        pagesMenu.add(adminItem);

        menuBar.add(pagesMenu);
        menuBar.add(new Menu("Edit"));
        menuBar.add(new Menu("Help"));
        setMenuBar(menuBar);
    }

    private Panel createLoginPanel() {
        Panel loginPanel = new Panel(new GridBagLayout());
        loginPanel.setBackground(Color.lightGray);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        Label title = new Label("SIMPLE JAVA AWT LAYOUT", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(title, gbc);

        gbc.gridwidth = 1;
        loginPanel.add(new Label("Username:"), gbc); gbc.gridx = 1;
        loginPanel.add(new TextField(15), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        loginPanel.add(new Label("Password:"), gbc); gbc.gridx = 1;
        TextField passField = new TextField(15);
        passField.setEchoChar('*');
        loginPanel.add(passField, gbc);

        Button loginButton = new Button("LOGIN");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        return loginPanel;
    }

    private Panel createStudentPanel() {
        Panel studentPanel = new Panel(new BorderLayout());
        studentPanel.setBackground(new Color(220, 255, 220));
        Label content = new Label(
                "Welcome Student! Showing your full name and registration number.",
                Label.CENTER
        );
        studentPanel.add(content, BorderLayout.CENTER);
        return studentPanel;
    }

    private Panel createAdminPanel() {
        Panel adminPanel = new Panel(new BorderLayout());
        adminPanel.setBackground(new Color(255, 220, 220));
        Label content = new Label(
                "Welcome Administrator! Displaying the administrative dashboard.",
                Label.CENTER
        );
        adminPanel.add(content, BorderLayout.CENTER);
        return adminPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Login".equals(command)) {
            cardLayout.show(contentPanel, LOGIN_CARD);
        } else if ("Student".equals(command)) {
            cardLayout.show(contentPanel, STUDENT_CARD);
        } else if ("Admin".equals(command)) {
            cardLayout.show(contentPanel, ADMIN_CARD);
        }
    }

    public static void main(String[] args) {
        new AWTMenuApp();
    }
}