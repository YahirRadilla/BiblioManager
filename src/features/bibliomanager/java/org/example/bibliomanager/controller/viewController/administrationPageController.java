package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.helpers.DialogManager;
import org.example.bibliomanager.helpers.Header;
import org.example.bibliomanager.helpers.MyItem;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

import java.util.ArrayList;
import java.util.Comparator;

public class administrationPageController {

    BookRepositoryImplements bookRepository = new BookRepositoryImplements();
    DialogManager dialogManager;
    Header header = new Header();
    User user;
    ArrayList<Book> books;
    Book selecetedBook;
    String[] bookColumns = {"ID", "Titulo", "Autor", "Calificación", "Género", "Fecha", "ISBN"};

    @FXML
    private AnchorPane principalPanel;
    @FXML
    private MFXTableView<MyItem> table;

    public void setValues(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {

        books = bookRepository.getBooks();
        addHeader();
        setupTableView(7);
        addItemsToTableView();
    }

    @FXML
    protected void onDelete(){

        MyItem selectedItem = table.getSelectionModel().getSelectedValues().get(0);
        if (selectedItem != null) {
            table.getItems().remove(selectedItem);
            table.getSelectionModel().clearSelection();
        }

    }

    @FXML
    protected void onEdit(){

        MyItem selectedItem = table.getSelectionModel().getSelectedValues().get(0);
        for (Book book : books){

            if(book.getId() == (int) selectedItem.getProperties()[0]){
                selecetedBook = book;
                continue;
            }
        }
        dialogManager=new DialogManager(selecetedBook, principalPanel, "/org/example/bibliomanager/shared/dialogEditBookContent.fxml");
        dialogManager.setStatus("edit");
        dialogManager.showDialog();
            /*MyItem item = new MyItem(2, "Title()", "Author()", "Rating()", "Category()", "Date()", "Isbn()");
            table.getItems().remove(selectedItem);
            table.getItems().addFirst(item);
            table.getSelectionModel().replaceSelection(item);*/

    }

    private void setupTableView(int limit) {
        MFXTableColumn<MyItem> IdColumn = new MFXTableColumn<>(bookColumns[0], true, Comparator.comparingInt(item -> (int) item.getProperty(0)));
        IdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(cellItem -> cellItem.getProperty(0).toString()));
        table.getTableColumns().add(IdColumn);
        for (int i = 1; i < limit; i++) {
            int finalI = i;
            MFXTableColumn<MyItem> column = new MFXTableColumn<>(bookColumns[i], true, Comparator.comparing(item -> item.getProperty(finalI).toString()));
            column.setRowCellFactory(item -> new MFXTableRowCell<>(cellItem -> cellItem.getProperty(finalI).toString()));
            table.getTableColumns().add(column);
        }

        table.setFooterVisible(false);


    }

    private void addItemsToTableView() {
        for (Book book : books) {
            MyItem item = new MyItem(book.getId(), book.getTitle(), book.getAuthor(), book.getRating(), book.getCategory(), book.getDate(), book.getIsbn());
            table.getItems().add(item);

        }

    }

    private void addHeader() {
        header.addHeader("/org/example/bibliomanager/shared/header.fxml", user, principalPanel);
    }
}



