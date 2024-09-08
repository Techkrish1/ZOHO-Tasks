import java.util.*;
class Calculation{

    int date;
    int month;
    int year;
    boolean isValid = true;

    // Constructor
    public Calculation(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
        validateInputDate();
    }

    private void validateInputDate() {
        if (month > 12 || year <= 0 || month < 1 || year > 9999) {
            System.out.println("Please recheck the date you entered");
            isValid = false;
        } else if (month == 2 && date > 29) {
            System.out.println("February's date is not greater than 29");
            isValid = false;
        } else if (date > 31 || date < 1) {
            System.out.println("Date is not valid for the given month");
            isValid = false;
        }
    }

    public boolean isValid() {
        return isValid;
    }
    

    public int getNearestForthCenturyLeapYear(int year){
        int nearest = 0;
        for (int start = 1; start < 25; start++){
            if ( (start * 400) <= year){
                nearest = start * 400;
            } else{
                break;
            }
        }
        return nearest;
        
    }

    public boolean leapYear(int year){
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)){
            return true;
        }else{
            return false;
        }
    }

    public int countOddDays(int year){
        int noOfCenturies = year / 100;
        int balanceYear = year % 100;
        int leapYears = balanceYear / 4;
        int oddLeapYears = balanceYear - leapYears;
        return ((leapYears * 2) + oddLeapYears + (noOfCenturies * 5)) % 7;
        
    }

    public int currentYearOddDays(int date, int month, int year, int odddays){
        int[] noOfDaysInMonth = {31, 28, 31,30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean isGivenYearLear = leapYear(year);
        if (isGivenYearLear){
            noOfDaysInMonth[1] = 29;
        }
        int noOfDays = 0;
        for (int count = 0; count < month - 1; count++){
            noOfDays += noOfDaysInMonth[count];
        }
        int currentOddDays = (noOfDays + date) % 7;
        return (currentOddDays + odddays) % 7;
    }

}

class Calendar {
    public Calculation calculation;

    public void getInputDate(String inputDate) {
        if (inputDate.length() == 10){
            int givenDate = Integer.parseInt(inputDate.substring(0, 2));
            int givenMonth = Integer.parseInt(inputDate.substring(3, 5));
            int givenYear = Integer.parseInt(inputDate.substring(6, 10));

            calculation = new Calculation(givenDate, givenMonth, givenYear);
        }else{
            System.out.println("Enter valid date like 01/02/2024");
        }

        
    }

    

    public void printDayOfWeek() {
        if (calculation != null && calculation.isValid()) {
            String[] nameOfTheDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            int nearestLeapYear = calculation.getNearestForthCenturyLeapYear(calculation.year - 1);

            int oddDays = ((calculation.year - 1) - nearestLeapYear);
            int oddDaysTillTheYearBefore = calculation.countOddDays(oddDays);
            
            int resultDayIndex = calculation.currentYearOddDays(calculation.date,calculation.month,calculation.year, oddDaysTillTheYearBefore);
            System.out.println(nameOfTheDay[resultDayIndex]);
        }


        
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