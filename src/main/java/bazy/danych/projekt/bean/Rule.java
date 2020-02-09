package bazy.danych.projekt.bean;

public class Rule {

    private String id;
    private String description;

    public Rule(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Rule() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
