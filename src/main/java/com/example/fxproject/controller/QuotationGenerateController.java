package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.QuotationBo;
import com.example.fxproject.model.QuotationDTO;
import com.example.fxproject.view.tdm.QuotationTM;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class QuotationGenerateController implements Initializable {

    public TextArea txtDescription;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtQuotationId;
    @FXML private TextField txtClientID;
    @FXML private TextField txtAmount;
    @FXML private TextField txtServiceSelection;
    @FXML private VBox serviceDisplayBox;
    @FXML private TableView<QuotationTM> quotationTable;
    @FXML private TableColumn<QuotationTM, String> colQuotationId;
    @FXML private TableColumn<QuotationTM, String> colClientId;
    @FXML private TableColumn<QuotationTM, String> colDescription;
    @FXML private TableColumn<QuotationTM, Double> colAmount;
    @FXML private TableColumn<QuotationTM, LocalDate> colDate;

    private final QuotationBo quotationBo = (QuotationBo) BOFactory.getInstance().getBO(BOFactory.BOType.QUOTATION);
    private final Map<String, Double> servicesMap = new HashMap<>();
    private final ContextMenu suggestionsMenu = new ContextMenu();
    private final List<Double> selectedServicePrices = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactories();
        loadTableData();
        datePicker.setValue(LocalDate.now());
        setupServiceData();
        setupAutoSuggestion();

        quotationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedQuo) -> {
            if (selectedQuo != null) {
                txtQuotationId.setText(selectedQuo.getQuotation_id());
                txtClientID.setText(selectedQuo.getClient_id());
                txtDescription.setText(selectedQuo.getDescription());
                txtAmount.setText(String.valueOf(selectedQuo.getAmount()));
                datePicker.setValue(selectedQuo.getDate());
            }
        });

        setDateRestriction();
        txtQuotationId.setText(generateNewId());
    }

    private void setCellValueFactories() {
        colQuotationId.setCellValueFactory(new PropertyValueFactory<>("quotation_id"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setDateRestriction() {
        LocalDate today = LocalDate.now();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && !date.equals(today)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
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
        txtDescription.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) {
                suggestionsMenu.hide();
                return;
            }

            List<String> filtered = servicesMap.keySet().stream()
                    .filter(s -> s.toLowerCase().startsWith(newText.toLowerCase()))
                    .collect(Collectors.toList());

            if (filtered.isEmpty()) {
                suggestionsMenu.hide();
                return;
            }

            suggestionsMenu.getItems().clear();
            for (String service : filtered) {
                MenuItem mi = new MenuItem(service);
                mi.setOnAction(e -> {
                    addServiceChip(service);
                    txtDescription.clear(); // clear after adding
                    suggestionsMenu.hide(); // hide until user types again
                });
                suggestionsMenu.getItems().add(mi);
            }

            if (!suggestionsMenu.isShowing()) {
                suggestionsMenu.show(txtDescription, Side.BOTTOM, 0, 0);
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String serviceName = txtDescription.getText().trim();
                if (!serviceName.isEmpty()) {
                    addServiceChip(serviceName);
                    txtDescription.clear();
                    suggestionsMenu.hide();
                }
                event.consume();
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

        chip.setOnMouseClicked(event -> appendToDescription(serviceName));

        serviceDisplayBox.getChildren().add(chip);
        selectedServicePrices.add(price);
        updateTotalAmount();

        // Immediately update description when added
        appendToDescription(serviceName);
    }

    private void appendToDescription(String serviceName) {
        String currentText = txtDescription.getText();
        if (currentText == null || currentText.isEmpty()) {
            txtDescription.setText(serviceName);
        } else {
            txtDescription.setText(currentText + ", " + serviceName);
        }
    }

    private void updateTotalAmount() {
        double total = selectedServicePrices.stream().mapToDouble(Double::doubleValue).sum();
        txtAmount.setText(String.format("%.2f", total));
    }

    @FXML
    public void btnSave(ActionEvent actionEvent) {
        try {
            QuotationDTO dto = new QuotationDTO(
                    txtQuotationId.getText(),
                    txtClientID.getText(),
                    Double.parseDouble(txtAmount.getText()),
                    txtDescription.getText(),
                    datePicker.getValue()
            );
            if (quotationBo.saveQuotation(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Quotation saved!").show();
                clearForm();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save failed.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnUpdate(ActionEvent actionEvent) {
        try {
            QuotationDTO dto = new QuotationDTO(
                    txtQuotationId.getText(),
                    txtClientID.getText(),
                    Double.parseDouble(txtAmount.getText()),
                    txtDescription.getText(),
                    datePicker.getValue()
            );
            if (quotationBo.updateQuotation(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Updated!").show();
                clearForm();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed.").show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    @FXML
    public void btnDelete(ActionEvent actionEvent) {
        try {
            if (quotationBo.deleteQuotation(txtQuotationId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                clearForm();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadTableData() {
        try {
            List<QuotationDTO> allQuotations = quotationBo.getAllQuotation();
            quotationTable.setItems(FXCollections.observableArrayList(
                    allQuotations.stream()
                            .map(dto -> new QuotationTM(
                                    dto.getQuotationId(),
                                    dto.getClientId(),
                                    dto.getDescription(),
                                    dto.getAmount(),
                                    dto.getDate()
                            ))
                            .collect(Collectors.toList())
            ));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading quotations: " + e.getMessage()).show();
        }
    }

    private void clearForm() {
        txtQuotationId.setText(generateNewId());
        txtClientID.clear();
        txtAmount.clear();
        datePicker.setValue(LocalDate.now());
        serviceDisplayBox.getChildren().clear();
        txtDescription.clear();
        txtDescription.clear();
        selectedServicePrices.clear();
        updateTotalAmount();
    }

    private String generateNewId() {
        try {
            return quotationBo.generateNewQuotationId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new Quotation ID: " + e.getMessage()).show();
        }
        return "Q001";
    }

    @FXML
    public void btnQuotationReportJasper(ActionEvent actionEvent) {
        // TODO: Implement report generation
    }

    @FXML
    public void btnReset(ActionEvent actionEvent) {
        clearForm();
        loadTableData();
    }

}
