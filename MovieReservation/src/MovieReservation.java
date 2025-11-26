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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MovieReservation extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // --- DYNAMIC DATA VARIABLES ---
    private JPanel dashboardPanel; // We need to access this panel to clear/add items
    private ArrayList<MovieData> allMovies; // This list stores our data

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
                    MovieReservation frame = new MovieReservation();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MovieReservation() {
        setTitle("Cinema POS - Terminal 01");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768); 
        
        // 1. Initialize our Movie Database
        initMovieData();
        
        contentPane = new JPanel();
        contentPane.setBackground(BG_DARK);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        contentPane.add(createTopBar(), BorderLayout.NORTH);
        contentPane.add(createSidebar(), BorderLayout.WEST);
        
        // 2. Initialize the Dashboard Panel (Empty at first)
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setBackground(BG_DARK);
        dashboardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JScrollPane scroll = new JScrollPane(dashboardPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scroll, BorderLayout.CENTER);
        
        // 3. Load "All Movies" by default
        updateDashboard("ALL");
    }

    // --- DATA INITIALIZATION ---
    private void initMovieData() {
        allMovies = new ArrayList<>();
        
        // NOW SHOWING
        allMovies.add(new MovieData("Avengers: Secret Wars", "Rating: PG-13 | 2h 45m", "avengers.jpg", "NOW", 
                new String[]{"10:00 AM", "1:30 PM", "5:00 PM"}, new Color[]{BTN_PAST, BTN_OPEN, BTN_OPEN}));
        
        allMovies.add(new MovieData("Frozen III", "Rating: G | 1h 50m", "frozen.jpg", "NOW", 
                new String[]{"11:00 AM", "2:00 PM", "4:30 PM"}, new Color[]{BTN_PAST, BTN_SOLD, BTN_OPEN}));
        
        allMovies.add(new MovieData("Oppenheimer Re-Run", "Rating: R | 3h 00m", "oppenheimer.jpg", "NOW", 
                new String[]{"12:00 PM", "4:00 PM", "8:00 PM"}, new Color[]{BTN_PAST, BTN_OPEN, BTN_OPEN}));
        
        allMovies.add(new MovieData("Spider-Man: Beyond", "Rating: PG | 2h 10m", "spiderman.jpg", "NOW", 
                new String[]{"1:00 PM", "3:30 PM", "6:00 PM"}, new Color[]{BTN_OPEN, BTN_OPEN, BTN_SOLD}));

        // COMING SOON (Note: Button colors are generic Grey as they aren't playable yet)
        allMovies.add(new MovieData("Star Wars: New Order", "Coming Dec 25", "starwars.jpg", "SOON", 
                new String[]{"DEC 25", "PRE-ORDER"}, new Color[]{BTN_PAST, BTN_PAST}));
        
        allMovies.add(new MovieData("Toy Story 5", "Coming Summer 2026", "toystory.jpg", "SOON", 
                new String[]{"2026", "INFO"}, new Color[]{BTN_PAST, BTN_PAST}));
    }

    // --- LOGIC: FILTER & UPDATE DASHBOARD ---
    private void updateDashboard(String filterType) {
        // 1. Clear existing content
        dashboardPanel.removeAll();
        
        // 2. Loop through data and add matches
        for(MovieData m : allMovies) {
            boolean showIt = false;
            
            if(filterType.equals("ALL")) showIt = true;
            else if(filterType.equals("NOW") && m.category.equals("NOW")) showIt = true;
            else if(filterType.equals("SOON") && m.category.equals("SOON")) showIt = true;
            
            if(showIt) {
                dashboardPanel.add(createMovieCard(m));
                dashboardPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
            }
        }
        
        // 3. Refresh the screen
        dashboardPanel.revalidate();
        dashboardPanel.repaint();
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
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(40, 40, 40));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        // Creating Buttons with Logic Keys
        sidebar.add(createMenuButton("All Movies", "ALL"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Now Showing", "NOW"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Coming Soon", "SOON"));
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text, final String actionKey) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBackground(BG_CARD);
        btn.setForeground(TEXT_WHITE);
        btn.setFocusPainted(false);
        
        // === EVENT HANDLING: FILTERING ===
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDashboard(actionKey); // Call the refresh method
            }
        });
        
        return btn;
    }
    
    // Updated to take the object instead of raw params
    private JPanel createMovieCard(MovieData m) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); 
        
        // Image Handling
        JPanel posterPlaceholder = new JPanel();
        posterPlaceholder.setBackground(Color.BLACK);
        posterPlaceholder.setPreferredSize(new Dimension(70, 100));
        posterPlaceholder.setBorder(new LineBorder(Color.GRAY));
        JLabel lblPoster = new JLabel();
        lblPoster.setHorizontalAlignment(JLabel.CENTER);
        
        try {
            ImageIcon rawIcon = new ImageIcon(m.imagePath); 
            if (rawIcon.getIconWidth() > -1) {
                Image scaledImage = rawIcon.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH);
                lblPoster.setIcon(new ImageIcon(scaledImage));
            } else {
                lblPoster.setText("NO IMG");
                lblPoster.setForeground(Color.GRAY);
            }
        } catch (Exception e) { lblPoster.setText("ERR"); }
        posterPlaceholder.add(lblPoster);
        
        // Info Handling
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(BG_CARD);
        
        JLabel lblTitle = new JLabel(m.title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(TEXT_WHITE);
        
        JLabel lblDetails = new JLabel(m.details);
        lblDetails.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDetails.setForeground(Color.LIGHT_GRAY);
        
        infoPanel.add(lblTitle);
        infoPanel.add(lblDetails);
        
        // Time Buttons Handling
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setBackground(BG_CARD);
        
        for (int i = 0; i < m.times.length; i++) {
            JButton timeBtn = new JButton(m.times[i]);
            timeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            timeBtn.setForeground(Color.WHITE);
            timeBtn.setBackground(m.statusColors[i]);
            timeBtn.setFocusPainted(false);
            timeBtn.setPreferredSize(new Dimension(110, 40));
            
            // Only add click logic if it's "NOW" showing and the button is BLUE
            if (m.category.equals("NOW") && m.statusColors[i].equals(BTN_OPEN)) {
                final String movieName = m.title;
                final String timeSlot = m.times[i];
                
                timeBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        SeatSelectionWindow seatWindow = new SeatSelectionWindow(movieName, timeSlot);
                        seatWindow.setVisible(true);
                        
                        
                    }
                });
            } else {
                timeBtn.setEnabled(false); // Disable buttons for Past/Sold/Coming Soon
            }
            timePanel.add(timeBtn);
        }
        
        card.add(posterPlaceholder, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(timePanel, BorderLayout.EAST);
        
        return card;
    }
    
    // --- INTERNAL DATA CLASS ---
    // This acts as a simple "database" object
    class MovieData {
        String title;
        String details;
        String imagePath;
        String category; // "NOW" or "SOON"
        String[] times;
        Color[] statusColors;
        
        public MovieData(String t, String d, String img, String cat, String[] times, Color[] colors) {
            this.title = t;
            this.details = d;
            this.imagePath = img;
            this.category = cat;
            this.times = times;
            this.statusColors = colors;
        }
    }
}