package com.example.fxproject.view.tdm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;

public class EnrollTM {

    private final SimpleStringProperty enrollId;
    private final SimpleStringProperty clientId;
    private final SimpleStringProperty clientName;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty quotationId;
    private final SimpleObjectProperty<Date> date;
    private final SimpleStringProperty employeeId;
    private final SimpleObjectProperty<Date> startingDate;
    private final SimpleStringProperty description;

    public EnrollTM(String enrollId, String clientId, String clientName, String contact,
                    String quotationId, Date date, String employeeId, Date startingDate, String description) {
        this.enrollId = new SimpleStringProperty(enrollId);
        this.clientId = new SimpleStringProperty(clientId);
        this.clientName = new SimpleStringProperty(clientName);
        this.contact = new SimpleStringProperty(contact);
        this.quotationId = new SimpleStringProperty(quotationId);
        this.date = new SimpleObjectProperty<>(date);
        this.employeeId = new SimpleStringProperty(employeeId);
        this.startingDate = new SimpleObjectProperty<>(startingDate);
        this.description = new SimpleStringProperty(description);
    }

    // Getters
    public String getEnrollId() { return enrollId.get(); }
    public String getClientId() { return clientId.get(); }
    public String getClientName() { return clientName.get(); }
    public String getContact() { return contact.get(); }
    public String getQuotationId() { return quotationId.get(); }
    public Date getDate() { return date.get(); }
    public String getEmployeeId() { return employeeId.get(); }
    public Date getStartingDate() { return startingDate.get(); }
    public String getDescription() { return description.get(); }

    // Setters
    public void setEnrollId(String value) { enrollId.set(value); }
    public void setClientId(String value) { clientId.set(value); }
    public void setClientName(String value) { clientName.set(value); }
    public void setContact(String value) { contact.set(value); }
    public void setQuotationId(String value) { quotationId.set(value); }
    public void setDate(Date value) { date.set(value); }
    public void setEmployeeId(String value) { employeeId.set(value); }
    public void setStartingDate(Date value) { startingDate.set(value); }
    public void setDescription(String value) { description.set(value); }

    // Properties (for TableView bindings)
    public SimpleStringProperty enrollIdProperty() { return enrollId; }
    public SimpleStringProperty clientIdProperty() { return clientId; }
    public SimpleStringProperty clientNameProperty() { return clientName; }
    public SimpleStringProperty contactProperty() { return contact; }
    public SimpleStringProperty quotationIdProperty() { return quotationId; }
    public SimpleObjectProperty<Date> dateProperty() { return date; }
    public SimpleStringProperty employeeIdProperty() { return employeeId; }
    public SimpleObjectProperty<Date> startingDateProperty() { return startingDate; }
    public SimpleStringProperty descriptionProperty() { return description; }
}
