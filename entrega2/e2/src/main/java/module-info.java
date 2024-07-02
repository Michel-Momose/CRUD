module com.entrega2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires org.postgresql.jdbc;
    requires javafx.graphics;
    requires javafx.base;

    opens com.entrega2 to javafx.fxml;
    exports com.entrega2;
}
