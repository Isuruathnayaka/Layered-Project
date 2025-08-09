package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.ClientBo;
import com.example.fxproject.model.QuotationDTO;
import com.example.fxproject.view.tdm.ClientTM;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;


import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class QuotationGenerateController implements Initializable {

    public DatePicker datePicker;

    @FXML private TextField txtQuotationId;
    @FXML private TextField txtClientID;
    @FXML private TextField txtAmount;
    @FXML private TextArea  txtDescription;
    @FXML private DatePicker dataPicker;
    @FXML private TextField txtServiceSelection;
    @FXML private Button     btnAddService;
    @FXML private VBox       serviceDisplayBox;

    @FXML private TableView<ClientTM> quotationTable;
    @FXML private TableColumn<ClientTM, String> colQuotationId;
    @FXML private TableColumn<ClientTM, String> colClientId;
    @FXML private TableColumn<ClientTM, String> colDescription;
    @FXML private TableColumn<ClientTM, Double> colAmount;
    @FXML private TableColumn<ClientTM, LocalDate> colDate;

    ClientBo clientBo = (ClientBo) BOFactory.getInstance().getBO(BOFactory.BOType.CLIENT);
    private final Map<String, Double> servicesMap = new HashMap<>();
    private final ContextMenu suggestionsMenu = new ContextMenu();
    private final List<Double> selectedServicePrices = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now()); // Set today's date by default

        setupServiceData();
        setupAutoSuggestion();
        initTableCols();

        Platform.runLater(() -> {
            try { loadQuotationData(); } catch (SQLException e) { throw new RuntimeException(e); }
        });

        quotationTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ClientTM selected = quotationTable.getSelectionModel().getSelectedItem();
                if (selected != null) { populateFieldsFromDTO(selected); }
            }
        });
        LocalDate today = LocalDate.now();

        // Set today's date as default
        datePicker.setValue(today);

        // Disable all other dates
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date != null && !date.equals(today)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                    setTooltip(new Tooltip("Only today's date can be selected"));
                }
            }
        });
    }

    private void setupServiceData() {
        servicesMap.put("Lawn Mowing", 1500.0);
        servicesMap.put("Hedge Trimming", 2000.0);
        servicesMap.put("Garden Cleanup", 3000.0);
        servicesMap.put("Tree Pruning", 3500.0);
        servicesMap.put("Fertilizer Application", 2500.0);
        servicesMap.put("Flower Bed Maintenance", 2000.0);
        servicesMap.put("Weed Removal", 1800.0);
        servicesMap.put("Landscape Design Consultation", 5000.0);
    }

    private void setupAutoSuggestion() {
        txtServiceSelection.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                suggestionsMenu.hide();
                return;
            }
            List<String> filtered = servicesMap.keySet().stream()
                    .filter(s -> s.toLowerCase().startsWith(newText.toLowerCase()))
                    .collect(Collectors.toList());

            if (filtered.isEmpty()) { suggestionsMenu.hide(); return; }

            suggestionsMenu.getItems().clear();
            for (String service : filtered) {
                MenuItem mi = new MenuItem(service);
                mi.setOnAction(e -> {
                    txtServiceSelection.setText(service);
                    txtServiceSelection.positionCaret(service.length());
                    suggestionsMenu.hide();
                });
                suggestionsMenu.getItems().add(mi);
            }
            if (!suggestionsMenu.isShowing()) {
                suggestionsMenu.show(txtServiceSelection, Side.BOTTOM, 0, 0);
            }
        });

        txtServiceSelection.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addServiceChip(txtServiceSelection.getText().trim());
            }
        });
    }

    private void addServiceChip(String serviceName) {
        if (!servicesMap.containsKey(serviceName)) {
            new Alert(Alert.AlertType.WARNING, "Service not found").show();
            return;
        }
        double price = servicesMap.get(serviceName);
        Label chip = new Label(serviceName + " - Rs. " + price);
        chip.setStyle("-fx-padding:5;-fx-background-color:#E0FFE0;-fx-border-color:#A0A0A0;-fx-border-radius:5;-fx-background-radius:5;");
        serviceDisplayBox.getChildren().add(chip);
        txtServiceSelection.clear();
        suggestionsMenu.hide();
        selectedServicePrices.add(price);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        double total = selectedServicePrices.stream().mapToDouble(Double::doubleValue).sum();
        txtAmount.setText(String.format("%.2f", total));
    }

    public void btnSave(ActionEvent actionEvent) {
        try {
            String quotationId = txtQuotationId.getText();
            String clientId    = txtClientID.getText();
            double amount      = Double.parseDouble(txtAmount.getText());
            LocalDate date     = datePicker.getValue();

            if (date == null) { new Alert(Alert.AlertType.WARNING, "Please select a date.").show(); return; }

            String description = txtDescription.getText();

            QuotationDTO dto = new QuotationDTO(quotationId, clientId, amount, description, date);
            boolean isSaved = clientBo.saveQuotation(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Quotation saved successfully!").show();
                loadQuotationData();
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save quotation.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        try {
            String quotationId = txtQuotationId.getText();
            if (quotationId.isEmpty()) { new Alert(Alert.AlertType.WARNING, "No quotation selected.").show(); return; }

            String clientId = txtClientID.getText();
            double amount   = Double.parseDouble(txtAmount.getText());
            LocalDate date  = datePicker.getValue();

            if (date == null) { new Alert(Alert.AlertType.WARNING, "Please select a date.").show(); return; }

            String description = txtDescription.getText();

            QuotationDTO dto = new QuotationDTO(quotationId, clientId, amount, description, date);
            boolean ok = QuotationModel.updateQuotation(dto);

            if (ok) {
                new Alert(Alert.AlertType.INFORMATION, "Updated!").show();
                loadQuotationData();
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed.").show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error " + ex.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        String quotationId = txtQuotationId.getText();
        if (quotationId.isEmpty()) { new Alert(Alert.AlertType.WARNING, "Select a quotation.").show(); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete quotation " + quotationId + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        try {
            boolean ok = QuotationModel.deleteQuotation(quotationId);
            if (ok) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted.").show();
                loadQuotationData();
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void populateFieldsFromDTO(ClientTM dto) {
        txtQuotationId.setText(dto.getQuotationId());
        txtClientID.setText(dto.getClientId());
        txtAmount.setText(String.valueOf(dto.getAmount()));
        if (dto.getDate() != null) {
            dataPicker.setValue(dto.getDate());
        } else {
            datePicker.setValue(null);
        }
        txtDescription.setText(dto.getDescription());

        // Optionally, parse description and set service chips if you want
        serviceDisplayBox.getChildren().clear();
        selectedServicePrices.clear();

        // If you want to parse the description and show chips:
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            String[] items = dto.getDescription().split(", ");
            for (String item : items) {
                Label chip = new Label(item);
                chip.setStyle("-fx-padding:5;-fx-background-color:#FFEFD5;-fx-border-color:#A0A0A0;-fx-border-radius:5;-fx-background-radius:5;");
                serviceDisplayBox.getChildren().add(chip);

                // Try to recover price from servicesMap if string starts with service name
                servicesMap.keySet().stream()
                        .filter(item::startsWith)
                        .findFirst()
                        .ifPresent(s -> selectedServicePrices.add(servicesMap.get(s)));
            }
        }
        updateTotalAmount();
    }

    private void initTableCols() {
        colQuotationId.setCellValueFactory(new PropertyValueFactory<>("quotationId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadQuotationData() throws SQLException {

    }



    private void clearForm() {

        txtClientID.clear();
        txtAmount.clear();
        datePicker.setValue(LocalDate.now());
        txtDescription.clear();
        txtServiceSelection.clear();
        serviceDisplayBox.getChildren().clear();
        selectedServicePrices.clear();
        updateTotalAmount();
    }

    public void btnQuotationReportJasper(ActionEvent actionEvent) {

    }

    public void btnReset(ActionEvent actionEvent) { clearForm(); }
}
