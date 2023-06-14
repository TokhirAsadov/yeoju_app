package uz.yeoju.yeoju_app.repository.educationYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearsForSelected;
import uz.yeoju.yeoju_app.payload.educationYear.WeekRestDtoForDean;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EducationYearRepository extends JpaRepository<EducationYear,String> {

    Optional<EducationYear> findEducationYearByName(String name);


    @Query(value = "select id,name from groups where faculty_id=?1",nativeQuery = true)
    List<EducationYearsForSelected> getGroupsByFacultyId(String id);


    @Query(value = "select u.id,u.fullName,al.time, :week as week, :weekday as weekday,  :section as section, ad.door_name as room\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name LIKE :room and u.id=:userId",nativeQuery = true)
    Set<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserId(@Param("userId") String userId, @Param("room") String room, @Param("year") Integer year,@Param("week") Integer week, @Param("weekday") Integer weekday, @Param("section") Integer section);

    @Query(value = "select id,name from EducationYear order by createdAt desc",nativeQuery = true)
    Set<EducationYearsForSelected> getEducationYearsForSelected();

    @Query(value = "select w.id,w.start,w.sortNumber,w.course,w.eduType from WeekOfEducationYear w join EducationYear_WeekOfEducationYear EYWOEY on w.id = EYWOEY.weeks_id where EYWOEY.EducationYear_id=?1 and w.eduType =?2",nativeQuery = true)
    Set<WeekRestDtoForDean> getWeeksByEduIdAndEduType(String educationYearId, String eduType);
}
