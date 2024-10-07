module com.example.expense {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.apache.poi.ooxml;

    opens com.example.expense to javafx.fxml;
    exports com.example.expense;
}