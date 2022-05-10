package uz.yeoju.yeoju_app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.yeoju.yeoju_app.entity.Section;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

        /**======================== BO`LIMLAR ===========================**/
        Section section1 = new Section("Rahbariyat");
        Section section2 = new Section("Kadrlar bo`limi");
        Section section3 = new Section("Yuridek bo`limi");
        Section section4 = new Section("Jamoatchilik bilan aloqalar bo`limi Axborot xizmati");
        Section section5 = new Section("Ijro nazorati va murojaatlar bilan ishlash bo`limi");
        Section section6 = new Section("Kanselyariya");
        Section section7 = new Section("Arxiv");
        Section section8 = new Section("O`quv-uslubiy boshqarma");
        Section section9 = new Section("O`quv rejalari va fan dasturlarini joriy etish bo`limi");
        Section section10 = new Section("Registrator bo`limi");
        Section section11 = new Section("Talabalar amalyoti bo`limi");
        Section section12 = new Section("Ta'lim yo`nalishlari rahbarlari");
        Section section13 = new Section("Kafedralar va professor-o`qituvchilar");
        Section section14 = new Section("Talabalar bilan ishlash bo`limi");
        Section section15 = new Section("Koreya madaniyati markazi");
        Section section16 = new Section("Ilmiy bo`lim");
        Section section17 = new Section("Axborot texnologiyalari markazi");
        Section section18 = new Section("Dasturiy va texnik ta;minot bo`limi");
        Section section19 = new Section("Tarmoqlarni boshqarish va axborot xavfsizligi bo`limi");
        Section section20 = new Section("Axborot-resurs markazi");
        Section section21 = new Section("Innovatsiya markazi");
        Section section22 = new Section("Ta'lim sifatini nazorat qilish bo`limi");
        Section section23 = new Section("Xalqaro hamkorlik bo`limi");
        Section section24 = new Section("Uzluksiz ta'lim markazi");
        Section section25 = new Section("Marketing va bitiruvchilar bo`limi");
        Section section26 = new Section("Buxgalteriya");
        Section section27 = new Section("Tahririy-nashriyot bo`limi");
        Section section28 = new Section("Tibbiy bo`lim");
        Section section29 = new Section("Jamoat xavfsizligini ta'minlash bo`limi");
        Section section30 = new Section("Texnik foydalanish va xo`jalik bo`limi");
    }
}
