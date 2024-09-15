import java.util.*;
class Calculation{

    int date;
    int month;
    int year;
    int[] noOfDaysInMonth = {31, 28, 31,30, 31, 30, 31, 31, 30, 31, 30, 31};
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
            if (date > 29 || (!leapYear(year) && date > 28)){
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
    

    public int getNearestForthCenturyLeapYear(int year){   // Finding  Nearest Leap year to make our calulation more easy
        int nearest = 0;
        for (int start = 1; start < year; start++){
            if ( (start * 400) <= year){   // Fourth Century has 0 odd days.
                nearest = start * 400;  
            } else{
                break;
            }
        }
        return nearest;
        
    }

    public boolean leapYear(int year){ // Checking givenYear is Leap or not
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)){
            return true;
        }else{
            return false;
        }
    }

    public int countOddDays(int year){   // After Finding Nearest Leap year subtract this to given Year (Given Year - Nearest LeapYear) and find the odd days for remaining years
        int noOfCenturies = year / 100;
        int balanceYear = year % 100;
        int leapYears = balanceYear / 4;
        int oddLeapYears = balanceYear - leapYears;
        return ((leapYears * 2) + oddLeapYears + (noOfCenturies * 5)) % 7;
        
    }

    public int currentYearOddDays(int date, int month, int year, int odddays){
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "Septempber", "October","November", "December"};
        boolean isGivenYearLear = leapYear(year);
        if (isGivenYearLear){
            noOfDaysInMonth[1] = 29;
        }
        int noOfDays = 0;
        for (int count = 0; count < month - 1; count++){
            noOfDays += noOfDaysInMonth[count];
        }
        Calendar.printDayOfMonth((((noOfDays + 1) % 7) + odddays) % 7, noOfDaysInMonth[month - 1], monthName[month - 1]);
        int currentOddDays = (noOfDays + date) % 7;
        return (currentOddDays + odddays) % 7;
    }

}

class Calendar {
    public Calculation calculation;
    static String givenDate;

    public void getInputDate(String inputDate) {
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

    
    static String[] nameOfTheDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public void printDayOfWeek() {  
        if (calculation != null && calculation.isValid()) {   

            int nearestLeapYear = calculation.getNearestForthCenturyLeapYear(calculation.year - 1);

            int oddDays = ((calculation.year - 1) - nearestLeapYear);
            int oddDaysTillTheYearBefore = calculation.countOddDays(oddDays);
            
            int resultDayIndex = calculation.currentYearOddDays(calculation.date,calculation.month,calculation.year, oddDaysTillTheYearBefore); // It will give the final Odd Day Index
           
            System.out.println("--------------------------------------------");
            System.out.println(givenDate + " : " + nameOfTheDay[resultDayIndex]);
            System.out.println();
        }
     
    }

    public static void printDayOfMonth(int startday, int totalDays, String monthName){
        System.out.println();
        System.out.println(monthName + " Month calendar ");
        System.out.println("--------------------------------------------");
        for (int row = 0; row < nameOfTheDay.length; row++){
            System.out.printf("%5s",nameOfTheDay[row].substring(0,3));
            
        }
        System.out.println();
        for (int row = 0; row < startday; row++){
            System.out.printf("%5s", " ");
        }
        int count = 1;
        for (int row = 0; row < totalDays + startday; row++){
            if (count > startday){
                System.out.printf("%5d",count - startday);
            }else if ((count+startday) > totalDays){
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

public class GetDateofDay {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Date (DD/MM/YYYY) : ");
        String inputDate = input.next();

        
        Calendar calendar = new Calendar();
        calendar.getInputDate(inputDate);
        calendar.printDayOfWeek();
        

    }
}