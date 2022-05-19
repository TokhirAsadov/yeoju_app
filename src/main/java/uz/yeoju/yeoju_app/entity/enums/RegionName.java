package uz.yeoju.yeoju_app.entity.enums;

public enum RegionName {

    //BARCHA VILOYATLAR
    ANDIJON("Andijon viloyati"),
    FARGONA("Farg'ona viloyati"),
    NAMANGAN("Namangan viloyati"),
    TOSHKENT_VILOYATI("Toshkent viloyati"),
    TOSHKENT_SHAHRI("Toshkent shahri"),
    SIRDARYO("Sirdaryo viloyati"),
    JIZZAX("Jizzax viloyati"),
    SAMARQAND("Samarqand viloyati"),
    QASHQADARYO("Qashqadaryo viloyati"),
    SURXONDARYO("Surxondaryo viloyati"),
    NAVOIY("Navoiy viloyati"),
    BUXORO("Buxoro viloyati"),
    XORAZM("Xorazm viloyati"),
    QORAQALPOGISTON_RESPUBLIKASI("Qoraqalpog'iston respublikasi");

    private String originalName;

    RegionName(String originalName) {
        this.originalName = originalName;
    }
}
