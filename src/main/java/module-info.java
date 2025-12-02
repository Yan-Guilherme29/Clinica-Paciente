module org.clinicapaciente {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.clinicapaciente to javafx.fxml;
    exports org.clinicapaciente;
}