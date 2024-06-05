package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.helpers.DialogManager;
import org.example.bibliomanager.helpers.HandleErrors;
import org.example.bibliomanager.helpers.Header;
import org.example.bibliomanager.helpers.MyItem;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class AdministrationPageController {

    BookRepositoryImplements bookRepository = new BookRepositoryImplements();
    HandleErrors handleErrors = new HandleErrors();
    DialogManager dialogManager;
    Header header = new Header();
    User user;
    ArrayList<Book> books;
    Book selectedBook;
    String[] bookColumns = {"ID", "Titulo", "Autor", "Calificación", "Género", "Fecha", "ISBN"};

    String currentTable = "libros";
    ArrayList<String> tables;
    @FXML
    private StackPane stackContainer;
    @FXML
    private AnchorPane principalPanel;
    @FXML
    private AnchorPane tableMenuPanel;
    @FXML
    private MFXTableView<MyItem> table;
    @FXML
    private Label resourcesText;

    public void setValues(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {

        books = bookRepository.getBooks();
        addHeader();
        setupTableView(7);
        addItemsToTableView();
        tables=bookRepository.getTables();
        System.out.println(tables);
        addTableButtons(tables);

    }

    @FXML
    protected void onDelete(){

        MyItem selectedItem = table.getSelectionModel().getSelectedValues().getFirst();
        System.out.println((int) selectedItem.getProperties()[0]);
        String isDeleted = bookRepository.deleteBookById((int) selectedItem.getProperties()[0]);
        if(isDeleted.equals("deleted")){
            handleErrors.showSnackbar("El elemento ha sido borrado", stackContainer, true);
        }
        if(isDeleted.equals("not-deleted")){
            handleErrors.showSnackbar("El elemento no fue borrado, hay rentas pendientes", stackContainer, true);
            return;
        }
        table.getItems().remove(selectedItem);
        table.getSelectionModel().clearSelection();

    }

    @FXML
    protected void onEdit(){

        MyItem selectedItem = table.getSelectionModel().getSelectedValues().get(0);
        for (Book book : books){

            if(book.getId() == (int) selectedItem.getProperties()[0]){
                selectedBook = book;
                break;
            }
        }

        dialogManager=new DialogManager(selectedBook, stackContainer, "/org/example/bibliomanager/shared/dialogEditBookContent.fxml", table, selectedItem);
        dialogManager.setStatus("edit");
        dialogManager.showDialog();

    }

    @FXML
    protected void onCreate(){
        dialogManager = new DialogManager(stackContainer,"/org/example/bibliomanager/shared/dialogCreateBookContent.fxml", table);
        dialogManager.setStatus("create");
        dialogManager.showDialog();
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

    private void addTableButtons(ArrayList<String> tables){
        int buttonSpaces = 30;
        for (String table: tables){

            MFXButton newTableButton = new MFXButton();
            newTableButton.getStyleClass().add("aside-menu-buttons");
            newTableButton.setVisible(true);
            newTableButton.setLayoutX(resourcesText.getLayoutX() + 10);
            newTableButton.setLayoutY(resourcesText.getLayoutY() + buttonSpaces);
            newTableButton.setText(table);
            buttonSpaces+=30;
            newTableButton.setOnAction(actionEvent -> onTableMenuClick(table));
            tableMenuPanel.getChildren().add(newTableButton);
        }
    }


    private void onTableMenuClick(String table){
        if(Objects.equals(currentTable, table)){
            return;
        }

        this.table.getItems().clear();

    }

    private void addHeader() {
        header.addHeader("/org/example/bibliomanager/shared/header.fxml", user, principalPanel);
    }
}



