package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.UserDao;
import ua.nure.kn.sadurska.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;

    private final UserDao userDao;

    public static void main(String[] args) {
        final MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public MainFrame() {
        super();
        userDao = DaoFactory.getInstance().getUserDao();
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management"));
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    private AddPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showEditPanel(final User editableUser) {
        showPanel(new EditPanel(this, editableUser));
    }

    private void showPanel(final JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void showDetailsPanel(final User user) {
        showPanel(new DetailsPanel(this, user));
    }
}
