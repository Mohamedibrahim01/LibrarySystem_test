module com.mycompany.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    // فتح جميع الباكدجات للـ FXML
    opens com.mycompany.library to javafx.fxml;
    opens com.mycompany.library.model to javafx.base; // لعرض البيانات في الجدول

    // تصدير الباكدجات الأساسية
    exports com.mycompany.library;
    exports com.mycompany.library.model;
    
    // تصدير الباكدجات الفرعية (تأكد أن هذه المجلدات تحتوي على ملفات)
    exports com.mycompany.library.patterns.decorator;
    exports com.mycompany.library.patterns.strategy;
}