package uz.yeoju.yeoju_app.entity.enums;

public enum DaysOfWeek {
    DUSHANBA("MONDAY","ПОНЕДЕЛЬНИК"),
    SESHANBA("TUESDAY","ВТОРНИК"),
    CHORSHANBA("WEDNESDAY","СРЕДА"),
    PAYSHANBA("THURSDAY","ЧЕТВЕРГ"),
    JUMA("FRIDAY","ПЯТНИЦА"),
    SHANBA("SATURDAY","СУББОТА"),
    YAKSHANBA("SUNDAY","ВОСКРЕСЕНЬЕ");

    private String nameEn;
    private String nameRu;

    DaysOfWeek(String nameEn, String nameRu) {
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }
}
