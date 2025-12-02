module org.provapoo {
    requires javafx.controls;
    requires javafx.fxml;

    // 1. O FXML precisa **exportar** o pacote que contém a classe Main
    //    para que o LauncherImpl possa encontrá-la.
    exports org.clinicapaciente;

    // 2. O FXML precisa **abrir** o pacote que contém a classe MainController
    //    para que ele possa injetar (reflexão) os campos @FXML privados.
    opens org.provapoo.controller to javafx.fxml;
    opens org.provapoo.model to javafx.base;

    // Se você usa o pacote principal para Controllers ou FXMLs, abra-o também:
    // opens org.provapoo to javafx.fxml;

    // Inclua também todos os 'requires' das bibliotecas de persistência (Hibernate, JPA, etc.)
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires static lombok;
}