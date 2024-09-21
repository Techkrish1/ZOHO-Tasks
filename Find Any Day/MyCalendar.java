
class MyCalendar{

    private int date;
    private int month;
    private int year;
    private int[] noOfDaysInMonth = {31, 28, 31,30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "Septempber", "October","November", "December"};
    private final String[] nameOfTheDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private boolean isValid = true;
    private int resultDayIndex;
    private int oddDaysTillTheYearBefore;

    // Constructor
    public MyCalendar(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
        validateInputDate();
        startCalculation();
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

    public void startCalculation(){   // From here to the calculation begins
        if (isValid == true) {   

            int nearestLeapYear = getNearestForthCenturyLeapYear();

            int balanceYear = ((year - 1) - nearestLeapYear);
            oddDaysTillTheYearBefore = countBalanceOddDaysTillPreviousYear(balanceYear);
            
            resultDayIndex = currentYearOddDays(oddDaysTillTheYearBefore); // It will give the final Odd Day Index
        }
    }
    

    private int getNearestForthCenturyLeapYear(){   // Finding  Nearest Leap year to make our calulation more easy
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

    private boolean isLeapYear(){ // Checking givenYear is Leap or not
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)){
            return true;
        }else{
            return false;
        }
    }

    private int countBalanceOddDaysTillPreviousYear(int balanceYears){   // After Finding Nearest Leap year subtract this to given Year (Given Year - Nearest LeapYear) and find the odd days for remaining years
        int noOfCenturies = balanceYears / 100;
        int subBalanceYear = balanceYears % 100;
        int leapYears = subBalanceYear / 4;
        int oddLeapYears = subBalanceYear - leapYears;
        return ((leapYears * 2) + oddLeapYears + (noOfCenturies * 5)) % 7;
        
    }

    private int countNumberOfDaysBeforeTheMonth(){
        int noOfDays = 0;
        if (isLeapYear() && month >= 2){
            noOfDaysInMonth[1] = 29;
        }
        for (int count = 0; count < month - 1; count++){
            noOfDays += noOfDaysInMonth[count];
        }
        return noOfDays;
    }

    private int currentYearOddDays(int odddays){
        int noOfDays = countNumberOfDaysBeforeTheMonth();
        int currentOddDays = (noOfDays + date) % 7;
        return (currentOddDays + odddays) % 7;
    }

    public void printDayOfWeek() {  
        if (isValid == true) {
            System.out.println("--------------------------------------------");
            System.out.println(date + "/" + month + "/" + year + " : " + nameOfTheDay[resultDayIndex]);
            System.out.println();
        }
        
    
    }

    private int helperPrintMonth(){
        return (((countNumberOfDaysBeforeTheMonth() + 1) % 7) + oddDaysTillTheYearBefore) % 7;
    }

    public void printDayOfMonth(){

        if (isValid == true){

            int startDayOfOddDay = helperPrintMonth();
            int totalDays = noOfDaysInMonth[month - 1];

            System.out.println();
            System.out.println(monthName[month - 1] + " Month calendar ");
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