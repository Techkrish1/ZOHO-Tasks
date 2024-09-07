import java.util.*;
class Calculation{
    

    public static void validationOfinputDate(int date, int month, int year){
        if (month == 2 && date > 29){
            System.out.println("February's date is not greater than 29");
        }
        if (date > 31 || month > 12 || year <= 400){
            System.out.println("Please recheck the date you entered");
        }
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
public class GetDateofDay {
    public static void main(String[] args){
        String[] nameOfTheDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Date (DD/MM/YYYY) : ");
        String inputDate = input.next(); // 12/11/2024
        int givenDate = Integer.parseInt(inputDate.substring(0, 2));
        int givenMonth = Integer.parseInt(inputDate.substring(3, 5));
        int givenYear = Integer.parseInt(inputDate.substring(6, 10));
        

        Calculation calculation = new Calculation();

        Calculation.validationOfinputDate(givenDate, givenMonth, givenYear);
        int nearestLeapYear = calculation.getNearestForthCenturyLeapYear(givenYear - 1);

        int oddDays = ((givenYear - 1) - nearestLeapYear);
        int oddDaysTillTheYearBefore = calculation.countOddDays(oddDays);
        
        int resultDayIndex = calculation.currentYearOddDays(givenDate,givenMonth,givenYear, oddDaysTillTheYearBefore);
        System.out.println(nameOfTheDay[resultDayIndex]);
        
        
        
    }
}