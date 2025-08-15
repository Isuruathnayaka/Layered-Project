package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.DashboardBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    @FXML
    private LineChart<String, Number> linechart;

    @FXML
    private CategoryAxis xAxisClients; // X Axis of chart

    @FXML
    private NumberAxis yAxisCounts;    // Y Axis of chart

    @FXML
    private AnchorPane ancMainLoader;

    @FXML
    private Label lblClientCount;

    @FXML
    private Label lblPlantCount;

    @FXML
    private Label lblActiveEmp1;

    @FXML
    private ImageView btnFacebookIcon;

    @FXML
    private ImageView btnGmail;

    @FXML
    private ImageView btnWhatsApp;

    @FXML
    private Label lblPaymentCount;

    @FXML
    private Label lblSavedClients;

    @FXML
    private Label lblQuotationCount;

    @FXML
    private Label lblEmployeeCount;

    private final DashboardBO dashboardBO =
            (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BOType.DASHBOARD);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDashboardCounts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDashboardCounts() throws Exception {
        int clientCount = dashboardBO.getClientCount();
        int enrollmentCount = dashboardBO.getEnrollmentCount();
        int paymentCount = dashboardBO.getPaymentCount();
        int quotationCount = dashboardBO.getQuotationCount();
        int employeeCount = dashboardBO.getEmployeeCount();

        // Labels
        lblClientCount.setText(String.valueOf(clientCount));
        lblSavedClients.setText(String.valueOf(enrollmentCount));
        lblPaymentCount.setText(String.valueOf(paymentCount));
        lblQuotationCount.setText(String.valueOf(quotationCount));
        lblEmployeeCount.setText(String.valueOf(employeeCount));

        // Chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Dashboard Stats");

        series.getData().add(new XYChart.Data<>("Clients", clientCount));
        series.getData().add(new XYChart.Data<>("Enrollments", enrollmentCount));
        series.getData().add(new XYChart.Data<>("Payments", paymentCount));
        series.getData().add(new XYChart.Data<>("Quotations", quotationCount));
        series.getData().add(new XYChart.Data<>("Employees", employeeCount));

        linechart.getData().clear();
        linechart.getData().add(series);
    }

    public void btnWhatsAppIcon(ActionEvent actionEvent) { }

    public void btnGmail(ActionEvent actionEvent) { }

    public void btnFacebookIcon(ActionEvent actionEvent) { }
}
