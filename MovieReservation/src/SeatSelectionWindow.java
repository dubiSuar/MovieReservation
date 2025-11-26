import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SeatSelectionWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private static HashSet<String> bookedSeats = new HashSet<>(); 
    private ArrayList<String> currentSelection = new ArrayList<>();

    private final Color BG_DARK = new Color(30, 30, 30);
    private final Color SEAT_RED = new Color(231, 76, 60);       
    private final Color SEAT_SELECTED = new Color(46, 204, 113); 
    private final Color SEAT_SOLD = new Color(127, 140, 141);    
    private final Color TEXT_WHITE = Color.WHITE;
    
    private final Dimension SEAT_SIZE = new Dimension(55, 40); 
    private final int AISLE_WIDTH = 30; 

    private JLabel lblSelectedSeatsDisplay;

    public SeatSelectionWindow(String movieTitle, String selectedTime) {
        setTitle("Select Seats - " + movieTitle + " (" + selectedTime + ")");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 750); 
        setLocationRelativeTo(null);
        setResizable(false); 

        contentPane = new JPanel();
        contentPane.setBackground(BG_DARK);
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        contentPane.add(createScreenLabel(), BorderLayout.NORTH);
        contentPane.add(createRowLettersPanel(), BorderLayout.WEST);
        contentPane.add(createMainSeatPanel(), BorderLayout.CENTER);
        contentPane.add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JLabel createScreenLabel() {
        JLabel lblScreen = new JLabel("SCREEN");
        lblScreen.setOpaque(true);
        lblScreen.setBackground(Color.GRAY);
        lblScreen.setForeground(Color.BLACK);
        lblScreen.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblScreen.setHorizontalAlignment(SwingConstants.CENTER);
        lblScreen.setPreferredSize(new Dimension(0, 50));
        lblScreen.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 50, 20, 50), 
                BorderFactory.createLineBorder(Color.GRAY, 0)
        ));
        return lblScreen;
    }

    private JPanel createRowLettersPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 1, 0, 15)); 
        panel.setBackground(BG_DARK);
        panel.setBorder(new EmptyBorder(25, 20, 25, 20)); 

        for (char c = 'A'; c <= 'J'; c++) {
            JLabel lblLetter = new JLabel(String.valueOf(c));
            lblLetter.setForeground(TEXT_WHITE);
            lblLetter.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblLetter.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(lblLetter);
        }
        return panel;
    }

    private JPanel createMainSeatPanel() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(BG_DARK);
        container.setBorder(new EmptyBorder(20, 0, 20, 0)); 

        JPanel rowA = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        rowA.setBackground(BG_DARK);
        for (int k = 1; k <= 6; k++) {
            rowA.add(createSeatButton("A" + k));
        }
        container.add(rowA);
        container.add(Box.createRigidArea(new Dimension(0, 15)));

        for (char rowChar = 'B'; rowChar <= 'J'; rowChar++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            rowPanel.setBackground(BG_DARK);

            for (int col = 1; col <= 5; col++) {
                rowPanel.add(createSeatButton(rowChar + "" + col));
            }
            rowPanel.add(Box.createRigidArea(new Dimension(AISLE_WIDTH, 1)));
            for (int col = 6; col <= 10; col++) {
                rowPanel.add(createSeatButton(rowChar + "" + col));
            }
            container.add(rowPanel);
            container.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        return container;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        lblSelectedSeatsDisplay = new JLabel("Selected: None");
        lblSelectedSeatsDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSelectedSeatsDisplay.setForeground(Color.WHITE);
        
        JButton btnConfirm = new JButton("CONFIRM BOOKING");
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConfirm.setBackground(new Color(52, 152, 219)); 
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);
        
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentSelection.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please select at least one seat.");
                    return;
                }
                
                bookedSeats.addAll(currentSelection);
                
                String receipt = "Seats Reserved Successfully!\n\n" +
                                 "Seats: " + currentSelection.toString() + "\n" +
                                 "Total: " + currentSelection.size() + " seats\n\n" +
                                 "Please click OK to proceed to Payment.";
                
                JOptionPane.showMessageDialog(contentPane, receipt, "Proceed to Payment", JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
            }
        });

        panel.add(lblSelectedSeatsDisplay, BorderLayout.WEST);
        panel.add(btnConfirm, BorderLayout.EAST);
        
        return panel;
    }

    private JButton createSeatButton(String seatId) {
        JButton btn = new JButton(seatId);
        btn.setPreferredSize(SEAT_SIZE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12)); 
        btn.setMargin(new Insets(0,0,0,0)); 
        btn.setBorder(BorderFactory.createLineBorder(BG_DARK.brighter(), 2)); 
        btn.setFocusPainted(false);

        if(bookedSeats.contains(seatId)) {
            btn.setBackground(SEAT_SOLD); 
            btn.setEnabled(false);        
        } else {
            btn.setBackground(SEAT_RED);  
        }

        btn.addActionListener(e -> {
            if (!btn.isEnabled()) return; 

            if (currentSelection.contains(seatId)) {
                currentSelection.remove(seatId);
                btn.setBackground(SEAT_RED);
            } else {
                currentSelection.add(seatId);
                btn.setBackground(SEAT_SELECTED);
            }
            
            updateSelectionLabel();
        });

        return btn;
    }
    
    private void updateSelectionLabel() {
        if (currentSelection.isEmpty()) {
            lblSelectedSeatsDisplay.setText("Selected: None");
        } else {
            lblSelectedSeatsDisplay.setText("Selected: " + currentSelection.toString());
        }
    }
}