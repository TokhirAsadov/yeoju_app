package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowWeek {
   private List<Show> first;
   private List<Show> second;
   private List<Show> third;
   private List<Show> fourth;
   private List<Show> fifth;
   private List<Show> sixth;
   private List<Show> seventh;
   private List<Show> eighth;
   private List<Show> ninth;
   private List<Show> tenth;
   private List<Show> eleventh;
   private List<Show> twelfth;
}
