import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int selectedCheckboxIndex = -1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 1.4");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel resultLabel = new JLabel("Dialog Result:");
        resultLabel.setBounds(50, 200, 150, 30);
        frame.add(resultLabel);

        JTextField inputField = new JTextField();
        inputField.setBounds(200, 200, 150, 30);
        frame.add(inputField);

        JButton yesNoCancel = new JButton("Yes/No/Cancel");
        yesNoCancel.setBounds(50, 50, 150, 30);
        frame.add(yesNoCancel);

        JButton abortRetryIgnore = new JButton("Abort/Retry/Ignore");
        abortRetryIgnore.setBounds(350, 50, 150, 30);
        frame.add(abortRetryIgnore);

        JButton fileOpen = new JButton("File Open");
        fileOpen.setBounds(200, 100, 150, 30);
        frame.add(fileOpen);

        JButton customDialog = new JButton("Custom Dialog");
        customDialog.setBounds(50, 150, 150, 30);
        frame.add(customDialog);

        JButton whichOption = new JButton("Which Option");
        whichOption.setBounds(350, 150, 150, 30);
        frame.add(whichOption);

        JButton exit = new JButton("Exit");
        exit.setBounds(200, 300, 150, 30);
        frame.add(exit);

        // Initially disable the "Which Option" button
        whichOption.setEnabled(false);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        yesNoCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Choose an option", "Yes/No/Cancel Dialog",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                switch (option) {
                    case JOptionPane.YES_OPTION:
                        option = 1; // Yes
                        break;
                    case JOptionPane.NO_OPTION:
                        option = 2; // No
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        option = 3; // Cancel
                        break;
                }

                updateResultLabel(option, inputField, resultLabel);
            }
        });

        abortRetryIgnore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Abort", "Retry", "Ignore"};
                int option = JOptionPane.showOptionDialog(frame, "Choose an option", "Abort/Retry/Ignore Dialog",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        options, options[0]);

                switch (option) {
                    case 0:
                        option = 4; // Abort
                        break;
                    case 1:
                        option = 5; // Retry
                        break;
                    case 2:
                        option = 6; // Ignore
                        break;
                }

                updateResultLabel(option, inputField, resultLabel);
            }
        });

        fileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\temp");
                fileChooser.setFileFilter(new FileNameExtensionFilter("C/C++ Files (*.cpp, *.c, *.h)", "cpp", "c", "h"));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int returnValue = fileChooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    String filePath = selectedFile.getAbsolutePath();

                    inputField.setText(filePath);

                    System.out.println("Selected File: " + filePath);
                }
            }
        });


        customDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a panel to hold checkboxes and text field
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // Add checkboxes
                List<JCheckBox> checkBoxes = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    JCheckBox checkBox = new JCheckBox("Option " + i);
                    checkBoxes.add(checkBox);
                    panel.add(checkBox);

                    int finalI = i;
                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            selectedCheckboxIndex = finalI;
                        }
                    });
                }

                // Add text field
                JTextField textField = new JTextField();
                panel.add(textField);

                // Show the custom dialog with checkboxes and text field
                int option = JOptionPane.showConfirmDialog(frame, panel, "Enter your custom message",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Process the selected options
                if (option == JOptionPane.OK_OPTION) {
                    StringBuilder selectedOptions = new StringBuilder();
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            selectedOptions.append(checkBox.getText()).append(" ");
                        }
                    }

                    // Append selected options to the text field
                    String customMessage = textField.getText();
                    textField.setText(customMessage + " " + selectedOptions.toString().trim());

                    // Enable the "Which Option" button
                    // and set inputField text to customMessage
                    whichOption.setEnabled(true);
                    inputField.setText(customMessage);
                }
            }
        });

        whichOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText(" (Option " + selectedCheckboxIndex + ")");

            }
        });

        frame.setVisible(true);
    }

    private static void updateResultLabel(int option, JTextField inputField, JLabel resultLabel) {
        String inputText = inputField.getText();
        String optionText = getOptionText(option);
        inputField.setText(optionText);
    }

    private static String getOptionText(int option) {
        switch (option) {
            case 1:
                return "Yes";
            case 2:
                return "No";
            case 3:
                return "Cancel";
            case 4:
                return "Abort";
            case 5:
                return "Retry";
            case 6:
                return "Ignore";
            default:
                return "Unknown";
        }
    }
}
