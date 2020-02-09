package bazy.danych.projekt.dao.impl;

import bazy.danych.projekt.db.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class ConnectDao {

    @Autowired
    protected Connector connector;
}
