/*
  Here , I used the conccept from R.S Agarwal Book to fing the day of the given Date.
  ODD Days  -- { 0: sunday, 1: Monday, 2: Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday}

  Step 1:  Find Nearest 4th century year because 4th century year's odd day = 0(sunday)
  step 2: subtract Nearest 4th century year from given year 
  step 3: find the remaining year's odd days
  step 4: find ocurrent year's dd days until the given date.
  step 5: add current year and remaining years odd days and get remainder from 7

  this approach is vaild for finding the 1th to nth year's day

  Enter the Date (DD/MM/YYYY) : 13/4/2004

  Sample output....

April Month calendar
--------------------------------------------
  Sun  Mon  Tue  Wed  Thu  Fri  Sat
                        1    2    3
    4    5    6    7    8    9   10
   11   12   13   14   15   16   17
   18   19   20   21   22   23   24
   25   26   27   28   29   30
--------------------------------------------
13/4/2004 : Tuesday

 */

 import java.util.*;

 public class GetDateofDay {
    
     public static void main(String[] args){
         Scanner input = new Scanner(System.in);
         System.out.print("Enter the Date (DD/MM/YYYY) : ");
         String inputDate = input.next();
         MyCalendar myCalendar;
 
         String[] parts = inputDate.split("/"); // It seperates date into 3 parts (date, month, year)
         if (parts.length == 3){
             try {
                 int _date = Integer.parseInt(parts[0]);
                 int _month = Integer.parseInt(parts[1]);
                 int _year = Integer.parseInt(parts[2]);
 
                 myCalendar = new MyCalendar(_date, _month, _year);
                 myCalendar.printDayOfMonth();
                 myCalendar.printDayOfWeek();
             }catch (NumberFormatException e){
                 System.out.println("Enter a valid date like (01/02/2024)");
             }
             
         }else{
             System.out.println("Enter Valid Date....");
         } 
         input.close(); 
 
     }
 }