package gomedic.ui.table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

/**
 * Util class to change the table size.
 */
public class TableUtil {
    /**
     * Wraps the text inside the table column.
     */
    protected static <S, T> void setWrapTextColumn(TableColumn<S, T> column, Number width) {
        /*
         * Tableview cell word wrapping
         * Author: Wermerb
         * https://stackoverflow.com/questions/28782975/tableview-cell-word-wrapping/44128787
         */
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            public void updateItem(T field, boolean empty) {
                super.updateItem(field, empty);

                if (field != null) {
                    setTableCell(field.toString(), this, width);
                }
            }
        });
    }

    private static void setTableCell(String label, TableCell<?, ?> tableCell, Number width) {
        Text text = new Text(label);
        text.setStyle("-fx-padding: 10 0 10 0;"
                + "-fx-text-wrap: true;"
                + "-fx-text-alignment:center;");
        text.setWrappingWidth(width.floatValue() - 35.0);
        tableCell.setPrefHeight(text.getLayoutBounds().getHeight() + 10);
        tableCell.setGraphic(text);
    }
}
