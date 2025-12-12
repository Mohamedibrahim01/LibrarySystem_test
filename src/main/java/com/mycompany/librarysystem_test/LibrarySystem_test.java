package com.mycompany.librarysystem_test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class LibrarySystem_test extends JFrame {

    // Colors Palette (Modern Flat Colors)
    private final Color SIDEBAR_BG = new Color(33, 41, 54); // Dark Blue/Grey
    private final Color MAIN_BG = new Color(240, 242, 245); // Light Grey
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color ACCENT_COLOR = new Color(52, 152, 219); // Blue
    private final Color BUTTON_COLOR = new Color(46, 204, 113); // Green

    private JTextField txtTitle, txtAuthor;
    private JComboBox<String> comboType;
    private JTextArea displayArea;

    public LibrarySystem_test() {
        // Basic Setup
        setTitle("Library Management System (Graduation Project)");
        setSize(1000, 650); // Wider screen for dashboard look
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center screen
        setLayout(new BorderLayout());

        // --- 1. HEADER (Top Panel) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("📚 Library System");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(44, 62, 80));
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        JLabel lblSubtitle = new JLabel("Design Patterns: Factory, Singleton, Builder, Decorator, Observer");
        lblSubtitle.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblSubtitle.setForeground(Color.GRAY);
        headerPanel.add(lblSubtitle, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. SIDEBAR (Left Panel - Inputs & Controls) ---
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.setPreferredSize(new Dimension(300, 0));
        sidebarPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form Title
        JLabel lblForm = new JLabel("Manage Books");
        lblForm.setForeground(Color.LIGHT_GRAY);
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(lblForm);
        sidebarPanel.add(Box.createVerticalStrut(20));

        // Input Fields (Helper method used to styling)
        txtTitle = createStyledTextField();
        txtAuthor = createStyledTextField();
        
        String[] types = {"Artificial Intelligence", "Software Engineering", "Management"};
        comboType = new JComboBox<>(types);
        comboType.setBackground(Color.WHITE);
        comboType.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        addLabel("Book Title:", sidebarPanel);
        sidebarPanel.add(txtTitle);
        sidebarPanel.add(Box.createVerticalStrut(10));

        addLabel("Author Name:", sidebarPanel);
        sidebarPanel.add(txtAuthor);
        sidebarPanel.add(Box.createVerticalStrut(10));

        addLabel("Category:", sidebarPanel);
        sidebarPanel.add(comboType);
        sidebarPanel.add(Box.createVerticalStrut(30));

        // Buttons
        JButton btnAdd = createStyledButton("Add New Book", new Color(46, 204, 113)); // Green
        JButton btnBorrow = createStyledButton("Borrow Book", new Color(52, 152, 219)); // Blue
        JButton btnReturn = createStyledButton("Return Book", new Color(241, 196, 15)); // Yellow/Orange
        JButton btnDecorator = createStyledButton("Show Rare Edition", new Color(155, 89, 182)); // Purple
        JButton btnShow = createStyledButton("Refresh List", new Color(149, 165, 166)); // Grey

        sidebarPanel.add(btnAdd);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnBorrow);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnReturn);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDecorator);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnShow);

        add(sidebarPanel, BorderLayout.WEST);

        // --- 3. MAIN CONTENT (Center - Display) ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(MAIN_BG);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblList = new JLabel("Inventory Log");
        lblList.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblList.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(lblList, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBackground(Color.WHITE);
        displayArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Scroll Pane with clean border
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // ================= LOGIC (UNCHANGED) =================

        // 1. Add Action
        btnAdd.addActionListener(e -> {
            String t = txtTitle.getText();
            String a = txtAuthor.getText();
            String type = (String) comboType.getSelectedItem();

            if (t.isEmpty() || a.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE); 
                return;
            }

            Book b = BookFactory.createBook(type, t, a);
            b.addObserver(new Student("Graduation_Committee")); // Observer
            LibraryDatabase.getInstance().addBook(b);

            appendLog("✅ Added: " + b.getTitle() + " (" + type + ")");
            txtTitle.setText(""); txtAuthor.setText("");
        });

        // 2. Refresh/Show Action
        btnShow.addActionListener(e -> {
            displayArea.setText("");
            appendLog("--- 🔄 Current Inventory ---");
            List<Book> list = LibraryDatabase.getInstance().getBooks();
            if(list.isEmpty()) appendLog("No books in database.");
            
            for(Book b : list) {
                String st = b.isBorrowed() ? "[🔴 BORROWED]" : "[🟢 AVAILABLE]";
                appendLog(String.format("%-30s %s", b.getTitle(), st));
            }
        });

        // 3. Borrow Action
        btnBorrow.addActionListener(e -> process(txtTitle.getText(), "borrow"));

        // 4. Return Action
        btnReturn.addActionListener(e -> process(txtTitle.getText(), "return"));

        // 5. Decorator Action
        btnDecorator.addActionListener(e -> {
            String t = txtTitle.getText();
            if(t.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter book title first!"); return; }
            
            for(Book b : LibraryDatabase.getInstance().getBooks()){
                if(b.getTitle().equalsIgnoreCase(t)){
                    Book rare = new RareBookDecorator(b);
                    JOptionPane.showMessageDialog(this, 
                        "<html><b>Original:</b> " + b.getTitle() + "<br><b>Decorated:</b> " + rare.getTitle() + "</html>",
                        "Decorator Pattern Test", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    // --- Helper Methods for Styling ---

    private void addLabel(String text, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)), 
            new EmptyBorder(5, 5, 5, 5)));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void appendLog(String text) {
        displayArea.append(text + "\n");
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }

    // Logic for Borrow/Return
    private void process(String t, String action) {
        if(t.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter Book Title First!"); return; }
        
        for(Book b : LibraryDatabase.getInstance().getBooks()){
            if(b.getTitle().equalsIgnoreCase(t)){
                if(action.equals("borrow")) {
                    if(!b.isBorrowed()) {
                        b.setBorrowed(true);
                        appendLog("📘 Borrowed: " + b.getTitle());
                        JOptionPane.showMessageDialog(this, "You borrowed: " + b.getTitle());
                    } else JOptionPane.showMessageDialog(this, "Book is already borrowed!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    if(b.isBorrowed()) {
                        b.returnBook(); 
                        appendLog("📗 Returned: " + b.getTitle());
                        // Observer msg is handled inside Student class popup
                    } else JOptionPane.showMessageDialog(this, "Book is not borrowed!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found in database!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Apply Modern Look and Feel (Nimbus) if available
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback to default
        }
        SwingUtilities.invokeLater(() -> new LibrarySystem_test().setVisible(true));
    }
}