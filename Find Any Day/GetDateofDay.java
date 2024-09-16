import java.util.*;
class Calculation{

    int date;
    int month;
    int year;
    int[] noOfDaysInMonth = {31, 28, 31,30, 31, 30, 31, 31, 30, 31, 30, 31};
    String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "Septempber", "October","November", "December"};
    boolean isValid = true;

    // Constructor
    public Calculation(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
        validateInputDate();
    }

    private void validateInputDate() {
        if (month > 12 || year <= 0 || month < 1 || date > 31 || date < 1) {
            System.out.println("Please recheck the date you entered");
            isValid = false;
        } else if (month == 2){
            if (date > 29 || (!isLeapYear() && date > 28)){
                System.out.println("February date is wrong");
                isValid = false;
            }
            
        }else if (date > noOfDaysInMonth[month - 1] && month != 2) {
            System.out.println("Check the  date you entered! give valid date...");
            isValid = false;
        }
    }

    public boolean isValid() {   // To check the given date or Month or Year are valid
        return isValid;
    }
    

    public int getNearestForthCenturyLeapYear(){   // Finding  Nearest Leap year to make our calulation more easy
        int nearest = 0;
        for (int start = 1; start < (year - 1); start++){
            if ( (start * 400) <= (year - 1)){   // Fourth Century has 0 odd days.
                nearest = start * 400;  
            } else{
                break;
            }
        }
        return nearest;
        
    }

    public boolean isLeapYear(){ // Checking givenYear is Leap or not
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)){
            return true;
        }else{
            return false;
        }
    }

    public int countBalanceOddDaysTillPreviousYear(int balanceYears){   // After Finding Nearest Leap year subtract this to given Year (Given Year - Nearest LeapYear) and find the odd days for remaining years
        int noOfCenturies = balanceYears / 100;
        int subBalanceYear = balanceYears % 100;
        int leapYears = subBalanceYear / 4;
        int oddLeapYears = subBalanceYear - leapYears;
        return ((leapYears * 2) + oddLeapYears + (noOfCenturies * 5)) % 7;
        
    }

    private int countNumberOfDaysBeforeTheMonth(){
        int noOfDays = 0;
        if (isLeapYear() && month >= 2){
            noOfDays += 1;
        }
        for (int count = 0; count < month - 1; count++){
            noOfDays += noOfDaysInMonth[count];
        }
        return noOfDays;
    }

    public int currentYearOddDays(int odddays){
        int noOfDays = countNumberOfDaysBeforeTheMonth();
        int currentOddDays = (noOfDays + date) % 7;
        return (currentOddDays + odddays) % 7;
    }

    public int helperPrintMonth(int oddDaysTillTheYearBefore){
        return (((countNumberOfDaysBeforeTheMonth() + 1) % 7) + oddDaysTillTheYearBefore) % 7;
    }

}

class MyCalendar {
    public Calculation calculation;
    static String givenDate;
    static String[] nameOfTheDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private int resultDayIndex;
    private int oddDaysTillTheYearBefore;

    public void inputDateParts(String inputDate) {
        givenDate = inputDate;
         // 12/10/2024 Spliting by "/"
        String[] parts = inputDate.split("/");
        if (parts.length == 3){
            try {
                int _date = Integer.parseInt(parts[0]);
                int _month = Integer.parseInt(parts[1]);
                int _year = Integer.parseInt(parts[2]);

                calculation = new Calculation(_date, _month, _year);
            }catch (NumberFormatException e){
                System.out.println("Enter a valid date like (01/02/2024)");
            }
            
        }else{
            System.out.println("Enter Valid Date....");
        } 
    }

    public void startCalculation(){
        if (calculation != null && calculation.isValid()) {   

            int nearestLeapYear = calculation.getNearestForthCenturyLeapYear();

            int balanceYear = ((calculation.year - 1) - nearestLeapYear);
            oddDaysTillTheYearBefore = calculation.countBalanceOddDaysTillPreviousYear(balanceYear);
            
            resultDayIndex = calculation.currentYearOddDays(oddDaysTillTheYearBefore); // It will give the final Odd Day Index
        }
    }
    
    public void printDayOfWeek() {  
        if (calculation != null && calculation.isValid()) {
            System.out.println("--------------------------------------------");
            System.out.println(givenDate + " : " + nameOfTheDay[resultDayIndex]);
            System.out.println();
        }
        
    
    }

    public void printDayOfMonth(){

        if (calculation != null && calculation.isValid()){

            int startDayOfOddDay = calculation.helperPrintMonth(oddDaysTillTheYearBefore);
            int totalDays = calculation.noOfDaysInMonth[calculation.month - 1];
            if (calculation.month >= 2){
                totalDays += 1;
            }
            String monthName = calculation.monthName[calculation.month - 1];

            System.out.println();
            System.out.println(monthName + " Month calendar ");
            System.out.println("--------------------------------------------");
            
            for (int row = 0; row < nameOfTheDay.length; row++){
                System.out.printf("%5s",nameOfTheDay[row].substring(0,3));
                
            }
            System.out.println();

            int count = 1;
            for (int row = 0; row < totalDays + startDayOfOddDay; row++){
                if (count <= startDayOfOddDay){
                    System.out.printf("%5s", " ");
                }
                else if (count > startDayOfOddDay){
                    System.out.printf("%5d",count - startDayOfOddDay);
                }else if ((count+startDayOfOddDay) > totalDays){
                    break;
                }
                if ((count % 7) == 0){
                    System.out.println();
                }
                count += 1;
            }
            System.out.println();
        }
        
    }
}

public class GetDateofDay {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Date (DD/MM/YYYY) : ");
        String inputDate = input.next();
        input.close();
        
        MyCalendar myCalendar = new MyCalendar();
        myCalendar.inputDateParts(inputDate);
        myCalendar.startCalculation();
        myCalendar.printDayOfMonth();
        myCalendar.printDayOfWeek();
        

    }
}