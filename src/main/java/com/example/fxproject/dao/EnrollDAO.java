package com.example.fxproject.dao;

import com.example.fxproject.entity.Enroll;
import com.example.fxproject.model.EnrollDTO;
import com.example.fxproject.model.EnrollQuotationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface EnrollDAO extends CrudDAO<Enroll> {


    boolean save(Enroll dto) throws SQLException, ClassNotFoundException;

    boolean update(Enroll dto) throws SQLException, ClassNotFoundException;
}
