package br.com.dio.ui.custom.input;

import br.com.dio.model.MoveResultEnum;
import br.com.dio.model.Space;
import br.com.dio.service.BoardService;
import br.com.dio.service.EventEnum;
import br.com.dio.service.EventListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.Font;

import static br.com.dio.service.EventEnum.CLEAR_SPACE;
import static java.awt.Font.PLAIN;
import static java.util.Objects.nonNull;

public class NumberText extends JTextField implements EventListener {

    private final Space space;
    private final BoardService boardService;
    private final int col;
    private final int row;

    public NumberText(final Space space, final BoardService boardService, final int col, final int row) {
        this.space = space;
        this.boardService = boardService;
        this.col = col;
        this.row = row;

        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if (space.isFixed() && nonNull(space.getActual())) {
            this.setText(space.getActual().toString());
        }

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                // Not relevant for plain text fields
            }

            private void changeSpace() {
                SwingUtilities.invokeLater(() -> {
                    String text = getText();
                    if (text.isEmpty()) {
                        space.clearSpace();
                        return;
                    }

                    int value = Integer.parseInt(text);
                    MoveResultEnum result = boardService.changeValue(col, row, value);

                    if (result != MoveResultEnum.SUCCESS) {
                        switch (result) {
                            case FIXED_SPACE -> JOptionPane.showMessageDialog(null, "Jogada Inválida: esta célula é fixa!");
                            case INVALID_MOVE -> JOptionPane.showMessageDialog(null, "Jogada Inválida: o número já existe na linha, coluna ou bloco.");
                        }
                        setText("");
                    }
                });
            }
        });
    }

    @Override
    public void update(final EventEnum eventType) {
        if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())) {
            this.setText("");
        }
    }
}