package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.example.bibliomanager.controller.datasource.AuthorDatasource;
import org.example.bibliomanager.controller.datasource.GenreDatasource;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.controller.repositories.RentRepositoryImplements;
import org.example.bibliomanager.helpers.DialogManager;
import org.example.bibliomanager.helpers.HandleErrors;
import org.example.bibliomanager.helpers.Header;
import org.example.bibliomanager.helpers.MyItem;
import org.example.bibliomanager.model.entities.*;
import org.example.bibliomanager.model.repositories.AuthRepository;
import org.example.bibliomanager.model.repositories.RentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class AdministrationPageController {

    BookRepositoryImplements bookRepository = new BookRepositoryImplements();
    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    RentRepository rentRepository = new RentRepositoryImplements();
    GenreDatasource genreDatasource = new GenreDatasource();
    AuthorDatasource authorDatasource = new AuthorDatasource();
    HandleErrors handleErrors = new HandleErrors();
    DialogManager dialogManager;
    Header header = new Header();
    User user;
    ArrayList<Book> books;
    ArrayList<User> users;
    ArrayList<Rent> rents;
    ArrayList<Author> authors;
    ArrayList<Genre> genres;
    Book selectedBook;
    User selectedUser;
    Rent selectedRent;
    Author selectedAuthor;
    Genre selectedGenre;
    String[] bookColumns = {"ID", "Titulo", "Autor", "Calificación", "Género", "Fecha", "ISBN"};
    String[] userColumns = {"ID", "Nombre", "email", "teléfono", "dirección", "Fecha registro"};
    String[] rentColumns = {"ID", "Libro", "Usuario", "fecha prestamo", "Fecha recogido", "Fecha devolución", "Devuelto"};
    String[] authorAndGenreColumns = {"ID", "Nombre"};
    String currentTable = "Libros";
    String contentUrl = "";
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
    @FXML
    private Text tableTitle;

    public void setValues(User user) {
        this.user = user;
        addHeader();
    }

    @FXML
    public void initialize() {

        books = bookRepository.getBooks();
        tables=bookRepository.getTables();
        setupTableView(bookColumns);
        addItemsToTableView(currentTable);
        addTableButtons(tables);

    }

    @FXML
    protected void onDelete(){

        MyItem selectedItem = table.getSelectionModel().getSelectedValues().getFirst();
        String isDeleted = "";
        switch (currentTable){
            case "Libros":
                isDeleted = bookRepository.deleteBookById((int) selectedItem.getProperties()[0]);
                break;
            case "Usuarios":
                isDeleted = authRepository.deleteUser((int) selectedItem.getProperties()[0]);
                break;
            case "Prestamos":
                isDeleted = rentRepository.deleteRent((int) selectedItem.getProperties()[0]);
                break;
            case "Autores":
                isDeleted = authorDatasource.deleteAuthor((int) selectedItem.getProperties()[0]);
                break;
            case "Categorias":
                isDeleted = genreDatasource.deleteGenre((int) selectedItem.getProperties()[0]);
                break;
        }
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

        MyItem selectedItem = table.getSelectionModel().getSelectedValues().getFirst();
        int idSelectedItem = (int) selectedItem.getProperties()[0];
        contentUrl = "/org/example/bibliomanager/shared/dialogEditContent.fxml";
        switch (currentTable){
            case "Libros":
                books = bookRepository.getBooks();
                for (Book book : books){
                    if(book.getId() == idSelectedItem){
                        selectedBook = book;
                        break;
                    }
                }
                dialogManager=new DialogManager(selectedBook, stackContainer, contentUrl, table, selectedItem);
                dialogManager.setCurrentContent("book");
                break;
            case "Usuarios":
                users = authRepository.getUsers();
                for (User user : users){
                    if(user.getId() == idSelectedItem){
                        selectedUser = user;
                        break;
                    }
                }

                dialogManager=new DialogManager(selectedUser, stackContainer, contentUrl, table, selectedItem);
                dialogManager.setCurrentContent("user");
                break;
            case "Prestamos":
                rents = rentRepository.getRents();
                for (Rent rent : rents){
                    if(rent.getId() == idSelectedItem){
                        selectedRent = rent;
                        break;
                    }
                }
                dialogManager=new DialogManager(selectedRent, stackContainer, contentUrl, table, selectedItem);
                dialogManager.setCurrentContent("rentAd");
                break;
            case "Autores":
                authors = authorDatasource.getAuthors();
                for (Author author : authors){
                    if(author.getId() == idSelectedItem){
                        selectedAuthor = author;
                        break;
                    }
                }
                dialogManager=new DialogManager(selectedAuthor, stackContainer, contentUrl, table, selectedItem);
                dialogManager.setCurrentContent("author");
                break;
            case "Categorias":
                genres = genreDatasource.getGenres();
                for (Genre genre : genres){
                    if(genre.getId() == idSelectedItem){
                        selectedGenre = genre;
                        break;
                    }
                }
                dialogManager=new DialogManager(selectedGenre, stackContainer, contentUrl, table, selectedItem);
                dialogManager.setCurrentContent("genre");
                break;
        }

        dialogManager.setStatus("edit");
        dialogManager.showDialog();


    }

    public void setupTableView(String[] columns) {
        MFXTableColumn<MyItem> IdColumn = new MFXTableColumn<>(columns[0], true, Comparator.comparingInt(item -> (int) item.getProperty(0)));
        IdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(cellItem -> cellItem.getProperty(0).toString()));
        table.getTableColumns().add(IdColumn);
        for (int i = 1; i < columns.length; i++) {
            int finalI = i;
            MFXTableColumn<MyItem> column = new MFXTableColumn<>(columns[i], true, Comparator.comparing(item -> item.getProperty(finalI).toString()));
            column.setRowCellFactory(item -> new MFXTableRowCell<>(cellItem -> cellItem.getProperty(finalI).toString()));
            table.getTableColumns().add(column);

        }

        table.setFooterVisible(false);


    }

    public void addItemsToTableView(String currentTable) {
        switch (currentTable){
            case "Libros":
                for (Book book : books) {
                    MyItem item = new MyItem(book.getId(), book.getTitle(), book.getAuthor(), book.getRating(), book.getCategory(), book.getDate(), book.getIsbn());
                    table.getItems().add(item);
                }
                break;
            case "Usuarios":
                for (User user : users) {
                    MyItem item = new MyItem(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getDirection(), user.getRegisterDate());
                    table.getItems().add(item);
                }
                break;
            case "Prestamos":
                for (Rent rent : rents) {
                    MyItem item = new MyItem(rent.getId(), rent.getBookName(), rent.getUserEmail(), rent.getRentDate(), rent.getPickUpDate(), rent.getReturnDate(), rent.isReturned());
                    table.getItems().add(item);
                }
                break;
            case "Autores":
                for (Author author : authors) {
                    MyItem item = new MyItem(author.getId(), author.getName());
                    table.getItems().add(item);

                }
                break;
            case "Categorias":
                for (Genre genre : genres) {
                    MyItem item = new MyItem(genre.getId(), genre.getName());
                    table.getItems().add(item);
                }
                break;
        }


    }



    @FXML
    protected void onCreate(){

        dialogManager = new DialogManager(stackContainer, "/org/example/bibliomanager/shared/dialogCreateContent.fxml", table, this::onTableMenuClick);
        dialogManager.setStatus("create");
        switch (currentTable){
            case "Libros":
                dialogManager.setCurrentContent("book");
                break;
            case "Usuarios":
                dialogManager.setCurrentContent("user");
                break;
            case "Prestamos":
                dialogManager.setCurrentContent("rentAd");
                break;
            case "Autores":
                dialogManager.setCurrentContent("author");
                break;
            case "Categorias":
                dialogManager.setCurrentContent("genre");
                break;
        }

        dialogManager.showDialog();
    }


    private void addTableButtons(ArrayList<String> tables){

        int buttonSpaces = 30;
        for (String table: tables){
            if(!table.equals("Imagenes")){
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
    }

    public void onTableMenuClick(String table){

        this.table.getItems().clear();
        this.table.getTableColumns().clear();
        currentTable = table;
        System.out.println(currentTable);
        switch (currentTable){
            case "Libros":
                books = bookRepository.getBooks();
                setupTableView(bookColumns);
                addItemsToTableView(currentTable);
                tableTitle.setText(currentTable.toUpperCase().charAt(0) + currentTable.substring(1, currentTable.length()).toLowerCase());
                break;
            case "Usuarios":
                users = authRepository.getUsers();
                setupTableView(userColumns);
                addItemsToTableView(currentTable);
                tableTitle.setText(currentTable.toUpperCase().charAt(0) + currentTable.substring(1, currentTable.length()).toLowerCase());
                break;
            case "Prestamos":
                rents = rentRepository.getRents();
                setupTableView(rentColumns);
                addItemsToTableView(currentTable);
                tableTitle.setText(currentTable.toUpperCase().charAt(0) + currentTable.substring(1, currentTable.length()).toLowerCase());
                break;
            case "Autores":
                authors = authorDatasource.getAuthors();
                setupTableView(authorAndGenreColumns);
                addItemsToTableView(currentTable);
                tableTitle.setText(currentTable.toUpperCase().charAt(0) + currentTable.substring(1, currentTable.length()).toLowerCase());
                break;
            case "Categorias":
                genres = genreDatasource.getGenres();
                setupTableView(authorAndGenreColumns);
                addItemsToTableView(currentTable);
                tableTitle.setText(currentTable.toUpperCase().charAt(0) + currentTable.substring(1, currentTable.length()).toLowerCase());
                break;
        }
    }




    private void addHeader() {
        header.addHeader("/org/example/bibliomanager/shared/header.fxml", user, principalPanel);
    }
}



