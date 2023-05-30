package com.company;

import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import static com.company.ProjectConstants.*;


public class Main {

    public static void progInfo() {
        System.out.println(SEPARATOR);
        System.out.println("Hello!");
        System.out.println("This project will let you place an arbitrary number of strings in a hashing function!");
        System.out.println("It will allow you to specify if you would like to use linear or quadratic probing!");
        System.out.println("It will also allow you to display the analytics of how many collisions occur at a certain point!");
        System.out.println("The data will be rehashed if the structure reaches 70% full with an array of twice the size!");
    } // END progInfo METHOD

    public static void releaseNotes() {

        System.out.println(SEPARATOR);
        System.out.println("\t\tPROJECT RELEASE NOTES");
        System.out.println(HALFSEPARATOR);

        // ISSUE 1 ---------------------------------------------------------------------------------------------------

        System.out.println("\t1. DEFINITION OF A TOKEN");
        System.out.println("Error messages will often reference tokens in this program.");
        System.out.println("A token is a set of characters within the larger inputted value.");
        System.out.println(HALFSEPARATOR);

        // ISSUE 2 ---------------------------------------------------------------------------------------------------

        System.out.println("\t2. STATISTICAL INFORMATION:");
        System.out.println("Linear probing is determined by the formula POSITION = HASH-KEY + INCREMENT, where increment increases by one for every collision.");
        System.out.println("Quadratic probing is determined by the formula POSITION = HASH-KEY + INCREMENT^2, where increment increases by one for every collision.");
        System.out.println("Rehashing is done when the array is 70% full, or when a collision at the end of the array requires a rehash to be completed.");
        System.out.println("Twice the size of the previous data structure is allocated for the new array.");
        System.out.println("The maximum possible array size is " + MAXARRAYSIZE + " elements while the minimum array size is " + MINARRAYSIZE + " elements.");
        System.out.println("Strings must contain more than 0 tokens, but less than or equal to " + MAXTOKENS + " tokens (which is 70% of the maximum array size).");
        System.out.println("Positions in the array are numbered starting from 0 and up to MAXIMUM SIZE - 1.");
        System.out.println(HALFSEPARATOR);

        // ISSUE 3 ---------------------------------------------------------------------------------------------------

        System.out.println("\t3. ISSUE WITH SAYING YES OR NO AFTER THE PROGRAM INFO IS DISPLAYED:");
        System.out.println("If the string of characters 'yes' or 'no' are entered while surrounded by other characters (eg. ggyesgg),");
        System.out.println("the program will proceed as if only the word was entered.");
        System.out.println("If both words 'yes' and 'no' are present in the same line, or if they are both present in the formation described above,");
        System.out.println("the program will proceed with whatever option is provided first.");
        System.out.println("(eg. If 'yes and no' is entered as a string, the program will accept 'yes' and will proceed with showing the menu.)");
        System.out.println(HALFSEPARATOR);

        // ISSUE 3 ---------------------------------------------------------------------------------------------------

        System.out.println("\t4. LETTERS ONLY:");
        System.out.println("In order for the hash function to work, only letters a-z are acceptable in strings.");
        System.out.println("The calculation of the hash key will not work with numbers or extended characters.");
        System.out.println("Any tokens with extended characters or numbers will not be accepted by the program,");
        System.out.println("and will instead be discarded");
        System.out.println(HALFSEPARATOR);

        // ISSUE 4 ---------------------------------------------------------------------------------------------------

        System.out.println("\t5. TOKENS OF 6 CHARACTERS OR LESS:");
        System.out.println("Due to limitations with integer values, the program cannot compute any tokens with more than " + MAXTOKENLENGTH + " characters.");
        System.out.println("For this reason, any tokens with more than " + MAXTOKENLENGTH + " characters will be discarded and not added to the array.");
        System.out.println(HALFSEPARATOR);

        // ISSUE 5 ---------------------------------------------------------------------------------------------------

        System.out.println("\t6. HANDLING COLLISIONS WHEN THE ARRAY SIZE IS EXCEEDED:");
        System.out.println("If collisions cause the POSITION value to be greater than the size of the array, the array is rehashed to accommodate that value.");
        System.out.println("All data is then rehashed into its new locations.");
        System.out.println(HALFSEPARATOR);

        // ISSUE 7 ---------------------------------------------------------------------------------------------------

        System.out.println("\t7. WHEN LINEAR AND/OR QUADRATIC PROBING IS NOT POSSIBLE:");
        System.out.println("If there are collisions at the end of an array with size 6400 (maximum size), the values cannot be stored with that probing method");
        System.out.println("When collisions occur at the end of the structure, a rehash will occur.");
        System.out.println("However, once the array has reached the maximum data size, rehashing cannot occur.");
        System.out.println("As such, the program will display an error message.");
        System.out.println("This can occur for linear or quadratic probing.");
        System.out.println(HALFSEPARATOR);

        // ISSUE 8 ---------------------------------------------------------------------------------------------------

        System.out.println("\t8. ALL UNACCEPTED STRINGS:");
        System.out.println("If the string contains only unaccepted tokens, the program will still proceed to the menu.");
        System.out.println("However, error messages will be shown in all display methods, and the user will be instructed to enter a new string.");
        System.out.println(HALFSEPARATOR);

        // ISSUE 9 ---------------------------------------------------------------------------------------------------

        System.out.println("\t9. CAPITALIZATION");
        System.out.println("The program will accept capital letters, however, they will be converted to lowercase.");
        System.out.println(HALFSEPARATOR);

        System.out.println("\t10. MAXIMUM NUMBER OF DATA SIZES THAT CAN BE STORED:");
        System.out.println("Due to limits with arrays, only 100 data sizes can be stored for each probing recommendation.");
        System.out.println("These data sizes can be reset in the main menu");
        System.out.println(HALFSEPARATOR);

        System.out.println("\t11. MODULO ISSUES WHEN DISPLAYING STATISTICS:");
        System.out.println("Modulo operations may have odd formatting when statistics are displayed,");
        System.out.println("However, the right quantities will be displayed.");
        System.out.println(HALFSEPARATOR);

    } // END releaseNotes METHOD

    public static void exitMessage() {
        System.out.println(SEPARATOR);
        System.out.println("Thank you for using this program!");
        System.out.println("Have a good day! :)");
        System.out.println(SEPARATOR);
    } // END exitMessage METHOD

    public static void main(String[] args) {


        // FIELDS --------------------------------------------------------------------------------

        boolean done;
        boolean tokenizerDone;
        boolean stringDone;
        String response;
        String tempString;
        String garbage;
        int option;

        // SCANNERS --------------------------------------------------------------------------------

        Scanner s = new Scanner(System.in);

        // OBJECTS ---------------------------------------------------------------------------------
        Hashing hashing = new Hashing();

        // MAIN CLASS CODE BEGINS HERE --> DO LOOP FOR USER INPUT -----------------------------------

        // RUNNING PROGINFO METHOD ----------------------------------------------------------------------

        progInfo();

        // RESETTING THE RECOMMENDATION VALUES ------------------------------------------------------------
        hashing.clearStatistics();

        do {
            done = false;

            // ASKING THE USER IF THEY WOULD LIKE TO CONTINUE ----------------------------------------

            System.out.println(SEPARATOR);
            System.out.println("Would you like to add a new string");
            response = s.nextLine();

            // IF THE USERS SAYS YES TO CONTINUE --------------------------------

            if (response.toUpperCase().contains("YES")) {

                // ASKING THE USER TO ENTER A STRING ----------------------------------

                // FIRST DO LOOP - USED FOR LOOPING BACK TO HERE IF THE USER WANTS TO ENTER A NEW STRING -----

                do {

                    stringDone = false;

                    // SECOND DO LOOP - USED FOR MAKING SURE A VALID RESPONSE IS ENTERED

                    do {

                        tokenizerDone = false;
                        System.out.println(SEPARATOR);
                        System.out.println("Please enter a string containing less than 4480 tokens");
                        tempString = s.nextLine();

                        // TOKENIZER ---------------------------------------------------------

                        StringTokenizer mainTokenizer = new StringTokenizer(tempString);

                        // CHECKING IF THE STRING CONTAINS 0 TOKENS, OR MORE THAN THE MAX NUMBER OF TOKENS

                        if (mainTokenizer.countTokens() > MAXTOKENS || mainTokenizer.countTokens() == 0) {
                            System.out.println(SEPARATOR);
                            System.out.println("\t\tINPUT ERROR: Only strings with MORE than one token and LESS than or EQUAL TO 4480 tokens can be used");
                            System.out.println("\t\tPlease enter a different string");

                        } else {
                            tokenizerDone = true;
                        }

                    } while (!tokenizerDone);

                    // PASSING THE STRING TO THE OTHER CLASS -----------------------------------------------------

                    hashing.setInputtedString(tempString);

                    // ASKING THE USER WHAT THEY WOULD LIKE TO DO WITH THE DATA ----------------------------

                    do {
                        tokenizerDone = false;
                        System.out.println(SEPARATOR);
                        System.out.println("Please select an option");
                        System.out.println("1. Linear Probing - Sort the data structure based on a linear method, and print the array, number of collisions, and number of rehashes to the screen.");
                        System.out.println("2. Quadratic Probing - Sort the data structure based on a linear method, and print the array, number of collisions, and number of rehashes to the screen.");
                        System.out.println("3. Analysis - Displays both the data in the linear structure, the data in the quadratic structure, the statistics of both methods, and recommends a linear or a quadratic sort.");
                        System.out.println("4. New String - Allows for the entry of a new string.");
                        System.out.println("5. Reset Statistics - Resets the stored number of recommendations and data sizes associated with those recommendations.");
                        System.out.println("6. Release Notes - Displays known program errors and why certain conditions are set within the program.");
                        System.out.println("7. Exit - Exits the program.");

                        // IF THERE IS AN INT  SELECTED FOR THE CASE STATEMENT ---------------------------------------------

                        if (s.hasNextInt()) {
                            option = s.nextInt();
                            garbage = s.nextLine();

                            switch (option) {

                                // IF THE USER SELECTS LINEAR PROBING --------------------------------

                                case 1: {
                                    hashing.setSelection(LINEARPROBING);
                                    hashing.clearLinearValues();
                                    hashing.setArray();
                                    hashing.linearDisplay();
                                    break;

                                    // IF THE USER SELECTS QUADRATIC PROBING --------------------------------

                                }
                                case 2: {
                                    hashing.setSelection(QUADRATICPROBINIG);
                                    hashing.clearQuadraticValues();
                                    hashing.setArray();
                                    hashing.quadraticDisplay();
                                    break;

                                    // IF THE USER ANALYSIS --------------------------------


                                }
                                case 3: {

                                    // ANALYSIS WILL RUN LINEAR PROBING, THEN RUN QUADRATIC PROBING, THEN RUN A SPECIAL ANALYSIS METHOD
                                    // AFTER THIS, IT WILL GO AND ASK THE USER HOW THEY WANT TO STORE THE DATA AGAIN
                                    // THEN, THEY CAN SELECT LINEAR OR QUADRATIC

                                    // DOING EVERYTHING FOR LINEAR --------------------------------------------------

                                    hashing.setSelection(LINEARPROBING);
                                    hashing.clearLinearValues();
                                    hashing.setArray();
                                    hashing.linearDisplay();

                                    // DOING EVERYTHING FOR QUADRATIC -----------------------------------------------

                                    hashing.setSelection(QUADRATICPROBINIG);
                                    hashing.clearQuadraticValues();
                                    hashing.setArray();
                                    hashing.quadraticDisplay();

                                    // DOING THE SPECIAL ANALYSIS METHOD
                                    hashing.analysis();

                                    break;

                                }
                                case 4: {

                                    // THIS WILL ALLOW THE USER TO ENTER A NEW STRING
                                    // THEY WILL BE KICKED OUT OF THE LOOP FOR THE CASE STATEMENT,
                                    // BUT NOT THE FIRST ONE BEFORE ENTERING A STRING

                                    System.out.println(SEPARATOR);
                                    System.out.println("Request granted.");
                                    tokenizerDone = true;
                                    break;

                                } case 5: {

                                    // THIS WILL RUN THE CLEAR STATISTICS METHOD FROM THE HASHING CLASS
                                    // THAT WAY, IF THE STATISTICS FILL, THE USER DOESN'T NEED TO RESTART

                                    hashing.clearStatistics();
                                    System.out.println(SEPARATOR);
                                    System.out.println("Statistics cleared.");
                                    break;
                                }

                                case 6: {
                                    // PRINTING RELEASE NOTES
                                    releaseNotes();
                                    break;
                                }

                                // IF THE USER SELECTS EXIT ---------------------------------------------


                                case 7: {

                                    // THIS WILL ALLOW THE USER TO EXIT THE PROGRAM
                                    // EVERYTHING BOOLEAN MUST BE SET TO TRUE

                                    exitMessage();

                                    tokenizerDone = true;
                                    stringDone = true;
                                    done = true;
                                    break;

                                }

                                default: {
                                    System.out.println(SEPARATOR);
                                    System.out.println("\t\tINPUT ERROR: Please enter an integer from 1 to 7");
                                }
                            }

                            // IF USER DOES NOT SELECT AN INTEGER FOR THE CASE STATEMENT

                        } else {
                            System.out.println(SEPARATOR);
                            System.out.println("\t\tINPUT ERROR: Please enter a valid integer!");
                            garbage = s.nextLine();
                        }
                    } while (!tokenizerDone);

                } while (!stringDone);

                // IF THE USER SAYS THEY DO NOT WANT TO ENTER A STRING, THE PROGRAM WILL EXIT ---------------------------------

            } else if (response.toUpperCase().contains("NO")) {
                done = true;
                exitMessage();

                // IF THE USER ENTERS GARBAGE FOR THE FIRST YES OR NO SELECTION ----------------------

            } else {
                System.out.println(SEPARATOR);
                System.out.println("\t\tINPUT ERROR: Response must be YES or NO!");
            }

        } while (!done);

    }
}
