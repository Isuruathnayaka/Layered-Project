package com.example.fxproject.dao.impl;

import com.example.fxproject.bo.custom.SuperBO;
import com.example.fxproject.dao.QuotationDAO;
import com.example.fxproject.dao.SQLUtil;
import com.example.fxproject.entity.Employee;
import com.example.fxproject.entity.Quotation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuotationDAOImpl implements QuotationDAO {
    @Override
    public ArrayList<Quotation> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM quotation");
        ArrayList< Quotation> e,quotations = new ArrayList<>();

        ResultSetMetaData metaData = rst.getMetaData();
        int columnCount = metaData.getColumnCount();

        System.out.println("Columns in ResultSet:");
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData.getColumnName(i));
        }

        while (rst.next()) {
            quotations.add(new Quotation(
                    rst.getString("quotation_id"),
                    rst.getString("client_id"),
                    rst.getString("description"),
                    rst.getDouble("amount"),
                    rst.getDate("date").toLocalDate()

            ));
        }
        return quotations;
    }

    @Override
    public boolean save(Quotation dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.executeUpdate(
               "INSERT INTO quotation (quotation_id, client_id, description, amount, date) VALUES (?, ?, ?, ?, ?)",
               dto.getQuotation_id(),dto.getClient_id(),dto.getDescription(),dto.getAmount(),dto.getDate()
       );
    }

    @Override
    public boolean update(Quotation dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "UPDATE quotation SET client_id = ?, description = ?, amount = ?, date = ? WHERE quotation_id = ?",
                dto.getClient_id(),dto.getDescription(),dto.getAmount(),dto.getDate(),dto.getQuotation_id()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "DELETE FROM quotation WHERE quotation_id = ?",id
        );
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT quotation_id FROM  quotation ORDER BY quotation_id DESC LIMIT 1";
        ResultSet rst = SQLUtil.executeQuery(sql);

        if (rst.next()) {
            String lastId = rst.getString("quotation_id");  // e.g. "C00-006" or "C006"

            // Extract digits only (remove all non-digit characters)
            String numericPart = lastId.replaceAll("\\D", "");  // keeps digits only

            int newId = Integer.parseInt(numericPart) + 1;

            // Format with leading zeros (change pattern if needed)
            return String.format("Q%03d", newId);
        } else {
            return "Q001";
        }
    }

    @Override
    public Quotation search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

}
