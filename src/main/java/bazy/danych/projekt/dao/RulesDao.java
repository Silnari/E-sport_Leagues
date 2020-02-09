package bazy.danych.projekt.dao;

import bazy.danych.projekt.bean.Rule;

import java.util.List;

public interface RulesDao {

    Rule getRuleById(String id);

    int addRule(Rule rule);

    List<Rule> getAllRules();
}
