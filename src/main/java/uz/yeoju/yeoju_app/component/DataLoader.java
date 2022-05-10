package uz.yeoju.yeoju_app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.SectionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    public final SectionRepository sectionRepository;
    public final RoleRepository roleRepository;
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

        sectionRepository.saveAndFlush(section1);
        sectionRepository.saveAndFlush(section2);
        sectionRepository.saveAndFlush(section3);
        sectionRepository.saveAndFlush(section4);
        sectionRepository.saveAndFlush(section5);
        sectionRepository.saveAndFlush(section6);
        sectionRepository.saveAndFlush(section7);
        sectionRepository.saveAndFlush(section8);
        sectionRepository.saveAndFlush(section9);
        sectionRepository.saveAndFlush(section10);
        sectionRepository.saveAndFlush(section11);
        sectionRepository.saveAndFlush(section12);
        sectionRepository.saveAndFlush(section13);
        sectionRepository.saveAndFlush(section14);
        sectionRepository.saveAndFlush(section15);
        sectionRepository.saveAndFlush(section16);
        sectionRepository.saveAndFlush(section17);
        sectionRepository.saveAndFlush(section18);
        sectionRepository.saveAndFlush(section19);
        sectionRepository.saveAndFlush(section20);
        sectionRepository.saveAndFlush(section21);
        sectionRepository.saveAndFlush(section22);
        sectionRepository.saveAndFlush(section23);
        sectionRepository.saveAndFlush(section24);
        sectionRepository.saveAndFlush(section25);
        sectionRepository.saveAndFlush(section26);
        sectionRepository.saveAndFlush(section27);
        sectionRepository.saveAndFlush(section28);
        sectionRepository.saveAndFlush(section29);
        sectionRepository.saveAndFlush(section30);

        System.out.println("saved sections");
        System.out.println(section1.getId()+"\n"+section10.getId());

        /**========================  LAVOZIMLAR -- ROLES  ===========================**/

        //--------Ректор
        Role role1 = new Role("Rektor",section1);
        Role role2 = new Role("O`quv ishlari bo`yicha prorektor",section1);
        Role role3 = new Role("Innovatsiya va ilmiy ishlar bo`yicha prorektor",section1);
        Role role4 = new Role("Ta'lim sifati va xalqaro hamkorlik bo`yicha prorektor",section1);
        Role role5 = new Role("Moliya-xo`jalik ishlar bo`yicha prorektor",section1);

        //--------Кадрлар бўлими
        Role role6 = new Role("Bo`lim boshlig`i",section2);
        Role role7 = new Role("Bosh mutaxasis",section2);
        Role role8 = new Role("Inspektor",section2);

        //--------Юридик бўлим
        Role role9 = new Role("Yuriskonsult",section3);

        //--------Жамоатчилик билан алоқалар бўлими - Ахборот хизмати
        Role role10 = new Role("Bo`lim boshlig`i - Axborot xizmati rahbari",section4);
        Role role11 = new Role("Bosh mutaxassis",section4);
        Role role12 = new Role("Veb-dizayner",section4);

        //--------Ижро назорати ва мурожаатлар билан ишлаш бўлими (2)
        Role role13 = new Role("Bo`lim boshlig`i",section5);
        Role role14 = new Role("Yetakchi mutaxassis",section5);

        //--------Канцелярия
        Role role15 = new Role("Kantselyariya mudiri",section6);

        //--------Архив
        Role role16 = new Role("Arxivarius",section7);

        //--------Ўқув-услубий бошқарма
        Role role17 = new Role("Boshqarma boshlig`i",section8);

        //--------Ўқув режалари ва фан дастурларини жорий этиш бўлими
        Role role18 = new Role("Bo`lim boshlig`i",section9);
        Role role19 = new Role("Bosh mutaxassis",section9);
        Role role20 = new Role("Yetakchi mutaxassis",section9);
        Role role21 = new Role("Dispetcher",section9);

        //---------Регистратор бўлими
        Role role22 = new Role("Bo`lim boshlig`i",section10);
        Role role23 = new Role("Bosh mutaxassis",section10);
        Role role24 = new Role("Yetakchi mutaxassis",section10);
        Role role25 = new Role("Uslubchi",section10);
        Role role26 = new Role("Dispetcher",section10);

        //---------Талабалар амалиёти бўлими
        Role role27 = new Role("Bo`lim boshlig`i",section11);
        Role role28 = new Role("Bosh mutaxassis",section11);
        Role role29 = new Role("Uslubchi",section11);

        //---------Таълим йўналишлари раҳбарлари
        Role role30 = new Role("Ta'lim yo`nalishi rahbari",section12);
        Role role31 = new Role("Ta'lim yo`nalishi rahbari o`rinbosari",section12);
        Role role32 = new Role("Dispetcher",section12);

        //---------Кафедралар ва профессор-ўқитувчилар
        Role role33 = new Role("Kafedra mudiri",section13);
        Role role34 = new Role("Professor",section13);
        Role role35 = new Role("Dotsent",section13);
        Role role36 = new Role("Katta o`qituvchi",section13);
        Role role37 = new Role("O`qituvchi",section13);
        Role role38 = new Role("Assistent",section13);
        Role role39 = new Role("Ish yurituvchi",section13);

        //---------Талабалар билан ишлаш бўлими
        Role role40 = new Role("Bo`lim boshlig`i",section14);
        Role role41 = new Role("Yetakchi mutaxassis",section14);

        //---------Корея маданияти маркази
        Role role42 = new Role("Bo`lim boshlig`i",section15);
        Role role43 = new Role("Bosh mutaxassis",section15);

        //---------Илмий бўлим
        Role role44 = new Role("Bo`lim boshlig`i",section16);
        Role role45 = new Role("Yetakchi mutaxassis",section16);

        //---------Ахборот технологиялари маркази
        Role role46 = new Role("Markaz boshlig`i",section17);

        //---------Дастурий ва техник таъминот бўлими
        Role role47 = new Role("Bo`lim boshlig`i",section18);
        Role role48 = new Role("Dasturchi-muhandis",section18);
        Role role49 = new Role("Muhandis-elektronchi",section18);
        Role role50 = new Role("Texnik",section18);

        //---------Тармоқларни бошқариш ва ахборот хавфсизлиги бўлими
        Role role51 = new Role("Bo`lim boshlig`i",section19);
        Role role52 = new Role("Tizim administratori",section19);
        Role role53 = new Role("Ma'lumotlar bazasi administratori",section19);
        Role role54 = new Role("Tarmoq administratori",section19);
        Role role55 = new Role("Dasturchi-muhandis",section19);

        //---------Ахборот-ресурс маркази
        Role role56 = new Role("Markaz boshlig`i",section20);
        Role role57 = new Role("Yetakchi mutaxassis",section20);
        Role role58 = new Role("Bibliograf",section20);
        Role role59 = new Role("Kutubxonachi",section20);
        Role role60 = new Role("Kompyuter operatori",section20);

        //---------Инновация маркази
        Role role61 = new Role("Markaz boshlig`i",section21);
        Role role62 = new Role("Laboratoriya mudiri",section21);
        Role role63 = new Role("Laborant",section21);
        Role role64 = new Role("Muhandis-dizayner",section21);
        Role role65 = new Role("Muhandis-konstruktor",section21);
        Role role66 = new Role("Muhandis-elektronchi",section21);
        Role role67 = new Role("Muhandis-dasturchi",section21);

        //---------Таълим сифатини назорат қилиш бўлими
        Role role68 = new Role("Bo`lim boshlig`i",section22);
        Role role69 = new Role("Bosh mutaxassis",section22);
        Role role70 = new Role("Yetakchi mutaxassis",section22);

        //---------Халқаро ҳамкорлик бўлими
        Role role71 = new Role("Bo`lim boshlig`i",section23);
        Role role72 = new Role("Bosh mutaxassis",section23);

        //---------Узлуксиз таълим маркази
        Role role73 = new Role("Markaz boshlig`i",section24);
        Role role74 = new Role("Bosh mutaxassis",section24);
        Role role75 = new Role("Yetakchi mutaxassis",section24);

        //---------Маркетинг ва битирувчилар бўлими
        Role role76 = new Role("Bo`lim boshlig`i",section25);
        Role role77 = new Role("Bosh mutaxassis",section25);
        Role role78 = new Role("Marketolog",section25);

        //---------Бухгалтерия
        Role role79 = new Role("Bosh buxgalter",section26);
        Role role80 = new Role("Buxgalter",section26);
        Role role81 = new Role("Buxgalter-kassir",section26);

        //---------Таҳририй-нашриёт бўлими
        Role role82 = new Role("Bo`lim boshlig`i",section27);
        Role role83 = new Role("Tahrirlovchi",section27);
        Role role84 = new Role("Kompyuter operatori",section27);

        //---------Тиббий бўлим
        Role role85 = new Role("Shifokor",section28);
        Role role86 = new Role("Hamshira",section28);
        Role role87 = new Role("Psixolog",section28);

        //---------Жамоат хавфсизлигини таъминлаш бўлими
        Role role88 = new Role("Bo`lim boshlig`i",section29);
        Role role89 = new Role("Qoravul",section29);

        //---------Техник фойдаланиш ва хўжалик бўлими
        Role role90 = new Role("Bo`lim boshlig`i",section30);
        Role role91 = new Role("Bosh muhandis",section30);
        Role role92 = new Role("Mehnat va texnika xavfsizligi muhandisi",section30);
        Role role93 = new Role("Energetik",section30);
        Role role94 = new Role("Komendant",section30);
        Role role95 = new Role("Shofyor",section30);
        Role role96 = new Role("Ombor mudiri",section30);
        Role role97 = new Role("Elektrik",section30);
        Role role98 = new Role("Santexnik",section30);
        Role role99 = new Role("Farrosh",section30);
        Role role100 = new Role("Bog`bon",section30);
        Role role101 = new Role("Qozonxona operatori",section30);
        Role role102 = new Role("Duradgor",section30);


        roleRepository.saveAndFlush(role1);
        roleRepository.saveAndFlush(role2);
        roleRepository.saveAndFlush(role3);
        roleRepository.saveAndFlush(role4);
        roleRepository.saveAndFlush(role5);
        roleRepository.saveAndFlush(role6);
        roleRepository.saveAndFlush(role7);
        roleRepository.saveAndFlush(role8);
        roleRepository.saveAndFlush(role9);
        roleRepository.saveAndFlush(role10);
        roleRepository.saveAndFlush(role11);
        roleRepository.saveAndFlush(role12);
        roleRepository.saveAndFlush(role13);
        roleRepository.saveAndFlush(role14);
        roleRepository.saveAndFlush(role15);
        roleRepository.saveAndFlush(role16);
        roleRepository.saveAndFlush(role17);
        roleRepository.saveAndFlush(role18);
        roleRepository.saveAndFlush(role19);
        roleRepository.saveAndFlush(role20);
        roleRepository.saveAndFlush(role21);
        roleRepository.saveAndFlush(role22);
        roleRepository.saveAndFlush(role23);
        roleRepository.saveAndFlush(role24);
        roleRepository.saveAndFlush(role25);
        roleRepository.saveAndFlush(role26);
        roleRepository.saveAndFlush(role27);
        roleRepository.saveAndFlush(role28);
        roleRepository.saveAndFlush(role29);
        roleRepository.saveAndFlush(role30);
        roleRepository.saveAndFlush(role31);
        roleRepository.saveAndFlush(role32);
        roleRepository.saveAndFlush(role33);
        roleRepository.saveAndFlush(role34);
        roleRepository.saveAndFlush(role35);
        roleRepository.saveAndFlush(role36);
        roleRepository.saveAndFlush(role37);
        roleRepository.saveAndFlush(role38);
        roleRepository.saveAndFlush(role39);
        roleRepository.saveAndFlush(role40);
        roleRepository.saveAndFlush(role41);
        roleRepository.saveAndFlush(role42);
        roleRepository.saveAndFlush(role43);
        roleRepository.saveAndFlush(role44);
        roleRepository.saveAndFlush(role45);
        roleRepository.saveAndFlush(role46);
        roleRepository.saveAndFlush(role47);
        roleRepository.saveAndFlush(role48);
        roleRepository.saveAndFlush(role49);
        roleRepository.saveAndFlush(role50);
        roleRepository.saveAndFlush(role51);
        roleRepository.saveAndFlush(role52);
        roleRepository.saveAndFlush(role53);
        roleRepository.saveAndFlush(role54);
        roleRepository.saveAndFlush(role55);
        roleRepository.saveAndFlush(role56);
        roleRepository.saveAndFlush(role57);
        roleRepository.saveAndFlush(role58);
        roleRepository.saveAndFlush(role59);
        roleRepository.saveAndFlush(role60);
        roleRepository.saveAndFlush(role61);
        roleRepository.saveAndFlush(role62);
        roleRepository.saveAndFlush(role63);
        roleRepository.saveAndFlush(role64);
        roleRepository.saveAndFlush(role65);
        roleRepository.saveAndFlush(role66);
        roleRepository.saveAndFlush(role67);
        roleRepository.saveAndFlush(role68);
        roleRepository.saveAndFlush(role69);
        roleRepository.saveAndFlush(role70);
        roleRepository.saveAndFlush(role71);
        roleRepository.saveAndFlush(role72);
        roleRepository.saveAndFlush(role73);
        roleRepository.saveAndFlush(role74);
        roleRepository.saveAndFlush(role75);
        roleRepository.saveAndFlush(role76);
        roleRepository.saveAndFlush(role77);
        roleRepository.saveAndFlush(role78);
        roleRepository.saveAndFlush(role79);
        roleRepository.saveAndFlush(role80);
        roleRepository.saveAndFlush(role81);
        roleRepository.saveAndFlush(role82);
        roleRepository.saveAndFlush(role83);
        roleRepository.saveAndFlush(role84);
        roleRepository.saveAndFlush(role85);
        roleRepository.saveAndFlush(role86);
        roleRepository.saveAndFlush(role87);
        roleRepository.saveAndFlush(role88);
        roleRepository.saveAndFlush(role89);
        roleRepository.saveAndFlush(role90);
        roleRepository.saveAndFlush(role91);
        roleRepository.saveAndFlush(role92);
        roleRepository.saveAndFlush(role93);
        roleRepository.saveAndFlush(role94);
        roleRepository.saveAndFlush(role95);
        roleRepository.saveAndFlush(role96);
        roleRepository.saveAndFlush(role97);
        roleRepository.saveAndFlush(role98);
        roleRepository.saveAndFlush(role99);
        roleRepository.saveAndFlush(role100);
        roleRepository.saveAndFlush(role101);
        roleRepository.saveAndFlush(role102);

        System.err.println("saved roles");
        System.out.println(role1.getId()+" -> "+role1.getSection() +"\n"+role100.getId()+" -> "+role100.getSection());



    }
}
