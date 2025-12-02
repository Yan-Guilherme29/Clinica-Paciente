module org.clinicapaciente {
    requires javafx.controls;
    requires javafx.fxml;


    exports org.clinicapaciente;


    opens org.clinicapaciente.controller to javafx.fxml;
    opens org.clinicapaciente.model to javafx.base;


    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires static lombok;
}