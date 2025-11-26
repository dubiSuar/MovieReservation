import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TicketClassification extends JFrame {
	
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // UI Constants
    private final Color BG_DARK = new Color(30, 30, 30);
    private final Color BG_CARD = new Color(50, 50, 50);
    private final Color TEXT_WHITE = Color.WHITE;
    
    // Button Colors
    private final Color BTN_PAST = new Color(100, 100, 100); 
    private final Color BTN_OPEN = new Color(52, 152, 219);   
    private final Color BTN_SOLD = new Color(231, 76, 60);    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TicketClassification frame = new TicketClassification(null, null, null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TicketClassification(ArrayList<String> selectedSeats, String movieTitle, String showingTime) {
        setTitle("Ticket Classification - " + movieTitle + " (" + showingTime + ")");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 700);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(BG_DARK);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        contentPane.add(createTopBar(), BorderLayout.NORTH);
        contentPane.add(createTicketPanel(selectedSeats, movieTitle, showingTime), BorderLayout.CENTER);
    }

    // --- UI COMPONENT BUILDERS ---

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel lblInfo = new JLabel("CASHIER: John Doe  |  TERMINAL: ID-884  |  12:50 PM");
        lblInfo.setForeground(TEXT_WHITE);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton btnLogout = new JButton("LOG OUT");
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);

        topBar.add(lblInfo, BorderLayout.WEST);
        topBar.add(btnLogout, BorderLayout.EAST);
        return topBar;
    }
    
 // Update your main ticket panel to receive data
    private JPanel createTicketPanel(ArrayList<String> selectedSeats, String movieTitle, String showingTime) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_DARK);

        // --- MOVIE DETAILS ---
        JPanel movieCard = new JPanel(new GridLayout(3, 1));
        movieCard.setBackground(BG_CARD);
        movieCard.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblMovie = new JLabel("Movie: " + movieTitle);
        lblMovie.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMovie.setForeground(TEXT_WHITE);

        JLabel lblTime = new JLabel("Showing Time: " + showingTime);
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTime.setForeground(Color.LIGHT_GRAY);

        JLabel lblSeats = new JLabel("Selected Seats: " + selectedSeats.toString());
        lblSeats.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSeats.setForeground(Color.LIGHT_GRAY);

        movieCard.add(lblMovie);
        movieCard.add(lblTime);
        movieCard.add(lblSeats);

        mainPanel.add(movieCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- SEAT CLASSIFICATION PANEL ---
        JPanel classificationPanel = new JPanel();
        classificationPanel.setLayout(new BoxLayout(classificationPanel, BoxLayout.Y_AXIS));
        classificationPanel.setBackground(BG_DARK);

        JLabel lblClass = new JLabel("Assign Ticket Type per Seat:");
        lblClass.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblClass.setForeground(TEXT_WHITE);

        classificationPanel.add(lblClass);
        classificationPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (String seat : selectedSeats) {
            classificationPanel.add(createSeatClassifier(seat));
            classificationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel.add(classificationPanel);

        // --- CHECKOUT BUTTON ---
        JButton btnCheckout = new JButton("PROCEED TO CHECKOUT");
        btnCheckout.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCheckout.setBackground(new Color(52, 152, 219));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setFocusPainted(false);

        btnCheckout.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Purchase Completed!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setBackground(BG_DARK);
        checkoutPanel.add(btnCheckout);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(checkoutPanel);

        return mainPanel;
    }

    // seat classifier remains the same
    private JPanel createSeatClassifier(String seatName) {
        JPanel seatRow = new JPanel();
        seatRow.setLayout(new BoxLayout(seatRow, BoxLayout.X_AXIS));
        seatRow.setBackground(BG_CARD);
        seatRow.setBorder(new EmptyBorder(10, 10, 10, 10));
        seatRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lbl = new JLabel("Seat " + seatName);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(TEXT_WHITE);

        String[] types = {"Adult", "Student", "Senior/PWD"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        seatRow.add(lbl);
        seatRow.add(Box.createHorizontalGlue());
        seatRow.add(typeBox);

        return seatRow;
    }
}