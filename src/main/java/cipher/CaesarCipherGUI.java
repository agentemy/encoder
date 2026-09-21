package cipher;

import javax.swing.*;
import java.awt.*;

public class CaesarCipherGUI extends JFrame {

    private final JTextArea inputArea;
    private final JTextArea outputArea;
    private final JSpinner shiftSpinner;
    private final JLabel lettersLabel;
    private final CaesarCipher cipher;

    public CaesarCipherGUI() {
        super("Шифр Цезаря");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        cipher = new CaesarCipher();

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        settingsPanel.add(new JLabel("Сдвиг:"));
        shiftSpinner = new JSpinner(new SpinnerNumberModel(3, -25, 25, 1));
        settingsPanel.add(shiftSpinner);
        lettersLabel = new JLabel("Латинских букв: —");
        settingsPanel.add(lettersLabel);
        add(settingsPanel, BorderLayout.NORTH);

        // -------- Панель ввода/вывода --------
        inputArea = new JTextArea(6, 40);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        outputArea = new JTextArea(6, 40);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        outputArea.setBackground(new Color(245, 245, 245));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.add(new JScrollPane(inputArea));
        centerPanel.add(new JScrollPane(outputArea));
        add(centerPanel, BorderLayout.CENTER);

        JButton encryptButton = new JButton("Зашифровать");
        JButton decryptButton = new JButton("Расшифровать");
        JButton rot13Button    = new JButton("ROT13");
        JButton countButton    = new JButton("Подсчитать буквы");
        JButton clearButton    = new JButton("Очистить");

        encryptButton.addActionListener(e -> applyShift(true));
        decryptButton.addActionListener(e -> applyShift(false));
        rot13Button.addActionListener(e -> {
            String src = inputArea.getText();
            try {
                outputArea.setText(cipher.rot13(src));
            } catch (IllegalArgumentException ex) {
                showWarning(ex.getMessage());
            }
        });
        countButton.addActionListener(e -> {
            String src = inputArea.getText();
            try {
                int n = cipher.countLetters(src);
                lettersLabel.setText("Латинских букв: " + n);
            } catch (IllegalArgumentException ex) {
                showWarning(ex.getMessage());
            }
        });
        clearButton.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
            lettersLabel.setText("Латинских букв: —");
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        buttonsPanel.add(encryptButton);
        buttonsPanel.add(decryptButton);
        buttonsPanel.add(rot13Button);
        buttonsPanel.add(countButton);
        buttonsPanel.add(clearButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(600, 460);
        setLocationRelativeTo(null);
    }

    private void applyShift(boolean encrypt) {
        String src = inputArea.getText();
        int shift = (Integer) shiftSpinner.getValue();
        try {
            String result = encrypt
                    ? cipher.encrypt(src, shift)
                    : cipher.decrypt(src, shift);
            outputArea.setText(result);
        } catch (IllegalArgumentException ex) {
            showWarning(ex.getMessage());
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Ошибка", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CaesarCipherGUI gui = new CaesarCipherGUI();
            gui.setVisible(true);
        });
    }
}