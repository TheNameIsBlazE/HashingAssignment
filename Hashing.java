package com.company;

import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Arrays;

import static com.company.ProjectConstants.*;

import java.lang.Math;

public class Hashing {

    // FIELDS -----------------------------------------------------------------

    private String inputtedString;
    private String stringTemp;
    private String[] hashArray;
    private int[] linearRecommendedDataSizes = new int[MAXSTRINGS];
    private int[] quadraticRecommendedDataSizes = new int[MAXSTRINGS];
    private int[] bothRecommendedDataSizes = new int[MAXSTRINGS];
    private int[] neitherRecommendedDataSizes = new int[MAXSTRINGS];
    private int selection;
    private int hashArraySize = 0;
    private int position;
    private int linearCollisions = 0;
    private int quadraticCollisions = 0;
    private int linearRehashes = 0;
    private int quadraticRehashes = 0;
    private int linearStoredElements = 0;
    private int quadraticStoredElements = 0;
    private int quadraticCheckValue = 0;
    private int linearDataSize = 0;
    private int quadraticDataSize = 0;
    private int linearBlankCounter = 0;
    private int quadraticBlankCounter = 0;
    private int recommendLinear = 0;
    private int recommendQuadratic = 0;
    private int recommendBoth = 0;
    private int recommendNeither = 0;
    private boolean linearPossibility = true;
    private boolean quadraticPossibility = true;
    private boolean isPossible;

    // METHODS ---------------------------------------------------------------


    public void setInputtedString(String inputtedString) {
        this.inputtedString = inputtedString;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public void clearStatistics() {

        // THIS WILL CLEAR ALL OF THE TRACKERS
        // THAT WAY, THEY CAN BE RESET ON THE FLY WITHOUT RESTARTING THE PROGRAM

        recommendLinear = 0;
        recommendQuadratic = 0;
        recommendBoth = 0;
        recommendNeither = 0;

        for (int i = 0; i < MAXSTRINGS; i++) {
            linearRecommendedDataSizes[i] = INVALID;
            quadraticRecommendedDataSizes[i] = INVALID;
            bothRecommendedDataSizes[i] = INVALID;
            neitherRecommendedDataSizes[i] = INVALID;
        }
    }

    public void clearLinearValues() {

        // THIS IS USED TO CLEAR ALL OF THE LINEAR VALUES SO THAT THE HASH FUNCTION CAN RUN AGAIN

        linearCollisions = 0;
        linearRehashes = 0;
        linearDataSize = 0;
        linearBlankCounter = 0;
        linearPossibility = true;

    }

    public void clearQuadraticValues() {

        // THIS IS USED TO CLEAR ALL OF THE QUADRATIC VALUES SO THAT THE HASH FUNCTION CAN RUN AGAIN

        quadraticCollisions = 0;
        quadraticRehashes = 0;
        quadraticDataSize = 0;
        quadraticBlankCounter = 0;
        quadraticPossibility = true;

    }

    public void setArray() {

        // IF THE NEW HASH ARRAY SIZE WILL BE GREATER THAN THE MAXIMUM SIZE OF THE ARRAY, BREAK EVERYTHING

        if ((hashArraySize * 2) > MAXARRAYSIZE) {
            System.out.println(SEPARATOR);
            System.out.println("\t\tMAXIMUM ARRAY SIZE REACHED");

            // IF THE ARRAY CAN'T ASSIGN EVERYTHING DURING LINEAR PROBING, THE LINEARPOSSIBILITY BOOLEAN WILL BE SET TO FALSE
            // THIS WILL WARN THE USER TO USE QUADRATIC PROBING
            // ALSO, THE LINEAR REHASHES WILL BE DECREASED BY 1 SINCE IT CAN'T REHASH

            if (selection == LINEARPROBING) {
                linearPossibility = false;
                linearRehashes--;
            }

            // IF THE ARRAY CAN'T ASSIGN EVERYTHING DURING QUADRATIC PROBING, THEN QUADRATICPROBING WILL BE SET TO FALSE
            // THIS WIL WARN THE USER TO USE LINEAR PROBING
            // ALSO, THE QUADRATIC REHASHES WILL BE DECREASED BY 1 SINCE IT CAN'T REHASH

            if (selection == QUADRATICPROBINIG) {
                quadraticPossibility = false;
                quadraticRehashes--;
            }


        } else {

            // CONDITIONS FOR WHAT TO SET THE HASH ARRAY TO ---------------------------------------------------------------

            // IF THE HASH ARRAY IS 0 (MEANING NEW RUN) ---------------------------------------------------------------

            if (hashArraySize == 0) {

                hashArraySize = MINARRAYSIZE;

                // SETTING THE HASH ARRAY ---------------------------------------------------------------

                // BIGGEST VALUE IS SET FIRST, AND THEN ONLY CERTAIN PARTS OF THE ARRAY ARE USED
                // THIS WAY, A NEW ARRAY IS NOT MADE EVERY TIME

                hashArray = new String[MAXARRAYSIZE];

                // ONCE THIS IS ALL SET UP, RUN THE HASH FUNCTION ---------------------------------------------------------------\

                // IF THE HASH ARRAY IS NOT 0 ---------------------------------------------------------------

            } else {

                // DOUBLE THE ARRAY SIZE

                hashArraySize = hashArraySize * 2;

                // NULLING THE AREAS OF THE ARRAY THAT MUST BE NULLED

                for (int i = 0; i < hashArraySize; i++) {
                    hashArray[i] = null;
                }

                // ONCE THE ARRAY IS SET, RUN THE HASH FUNCTION ---------------------------------------------------------------

            }

            hashFunction();
        }


    }

    public void hashFunction() {

        // LOCAL VARIABLES ---------------------------------------------------------------------

        String lowercaseString;
        int binaryValue;
        boolean pass;
        boolean lengthCheck;

        // SINCE A REHASH OR A NEW HASH IS OCCURRING, RESET THE NUMBER OF LINEAR AND QUADRATIC ELEMENTS
        linearStoredElements = 0;
        quadraticStoredElements = 0;
        linearBlankCounter = 0;
        quadraticBlankCounter = 0;

        // SETTING UP THE BRAND NEW LOWERCASE STRING -------------------------------------

        lowercaseString = inputtedString.toLowerCase(Locale.ROOT);

        // STRING TOKENIZER -------------------------------------------------------------------

        // THIS WILL BE USED TO SPLIT UP THE STRING AT EVERY SPACE
        // THAT WAY, MULTIPLE STRINGS CAN BE READ IN ONE LINE

        StringTokenizer tokenizer = new StringTokenizer(lowercaseString);

        // TOKENIZER LOOP ---------------------------------------------------------

        for (int j = 1; tokenizer.hasMoreTokens(); j++) {

            // SETTING STRING TEMP ---------------------------------------------

            stringTemp = tokenizer.nextToken();

            // RESETTING ALL THE VALUES THAT NEED TO BE RESET AFTER EACH RUN -------------------------------------------

            position = 0;
            binaryValue = 0;
            pass = true;
            lengthCheck = true;

            // CALCULATOR FOR CALCULATING THE BINARY VALUE --------------------------------------------------

            // TEST TO SEE IF CODEPOINTAT FALLS WITHIN THE NEEDED VALUES
            // STRINGTEMP MUST HAVE ALL CODE POINT VALUES FALL BETWEEN
            // THE CODE POINT VALUES FOR LOWERCASE A AND LOWERCASE Z
            // THESE ARE DEFINED AS CONSTANTS IN PROIJECTCONSTANTS
            // IF THE STRING DETECTS ANY VALUES OUTSIDE OF THIS RANGE, THE STRING WILL BE DISCARDED
            // CODEPOINT WILL BE SET TO FALSE, AND THE LOOP WILL BE AVOIDED

            for (int k = 0; k < stringTemp.length(); k++) {
                if (pass) {
                    if (stringTemp.codePointAt(k) < LOWERCASE_a || stringTemp.codePointAt(k) > LOWERCASE_z) {
                        System.out.println(SEPARATOR);
                        System.out.println("\t\tWARNING: The string " + stringTemp + " has been discarded!  Strings MUST contain only LETTERS!!!");
                        pass = false;
                    }
                }
            }

            // CHECKING IF THE STRING IS LESS THAN OR EQUAL TO 6 CHARACTERS, BECAUSE INTEGERS BREAK BEYOND THAT POINT ---


            // CHECK IF PASS IS TRUE ---------------------------------------------------------------------------------
            // PASS WILL BE TRUE IF ALL THE VALUES IN STRING TEMP FALL WITHIN THE RANGE

            if (stringTemp.length() > MAXTOKENLENGTH) {
                System.out.println(SEPARATOR);
                System.out.println("\t\tWARNING: The string " + stringTemp + " has been discarded!  Tokens must be 6 OR LESS characters in length!!!");
                lengthCheck = false;
            }

            if (pass && lengthCheck) {

                // CHECK FOR A ONE LETTER LONG STRING --------------------------------------------------------

                // IF THIS OCCURS, THE KEY WILL BE THE NUMBER THAT IT IS IN THE ALPHABET (CODE POINT VALUE - LOWERCASE A MINUS ONE)

                if ((stringTemp.length() - 1) == 0) {
                    binaryValue = stringTemp.codePointAt(0) - LOWERCASE_a_MINUS_ONE;
                }

                // CHECK THE ABOVE TO MAKE SURE EVERYTHING IS WORKING PROPERLY

                // MAIN CALCULATION CODE ------------------------------------------------------------------------
                // THIS IS DONE ACCORDING TO THE EXAMPLE GIVEN IN CLASS

                for (int i = 0; i < stringTemp.length() - 1; i++) {
                    if (i == 0) {
                        binaryValue = (stringTemp.codePointAt(i) - LOWERCASE_a_MINUS_ONE) * MASTERBINARY + (stringTemp.codePointAt(i + 1) - LOWERCASE_a_MINUS_ONE);
                    } else {
                        binaryValue = binaryValue * MASTERBINARY + (stringTemp.codePointAt(i + 1) - LOWERCASE_a_MINUS_ONE);
                    }

                }

                // SETTING THE POSITION IN THE ARRAY --------------------------------------------------

                position = binaryValue % hashArraySize;

                // IF THE USER SELECTS LINEAR PROBING, THEN RUN LINEAR PROBING -----------------------

                if (selection == LINEARPROBING) {
                    linearProbing();
                    if (position >= hashArraySize || linearStoredElements > (hashArraySize * 0.7)) {
                        linearRehashes++;
                        setArray();
                        break;
                    }
                }

                // IF THE USER SELECTS QUADRATIC PROBING, THEN RUN QUADTATIC PROBING -----------------------

                if (selection == QUADRATICPROBINIG) {

                    // IN THE QUADRATIC PROBING METHOD, A DIFFERENT VALUE IS USED TO REPRESENT POSITION
                    // DUE TO THE NATURE OF THE FORMULA
                    // POSITION MUST BE INITIALLY SET TO THAT VALUE

                    quadraticCheckValue = position;
                    quadraticProbing();

                    // DOING THE REHASH IF A REHASH IS NEEDED ----------------------------------------------------

                    if (quadraticCheckValue >= hashArraySize || quadraticStoredElements > (hashArraySize * 0.7)) {
                        quadraticRehashes++;
                        setArray();
                        break;
                    }
                }

                // CHECKING THE STATISTICS FOR THE LINEAR REHASH

            }
        }

    } // END hashFunction METHOD

    public void linearProbing() {

        boolean done;

        do {
            done = false;

            // BREAKING THE LOOP ---------------------------------------------------------------

            // CHECKING IF THE POSITION VALUE IS GREATER THAN OR EQUAL TO THE MAXIMUM VALUE IN THE ARRAY
            // IT WILL ALSO CHECK IF THE ARRAY IS 70% FULL OR MORE

            if (position >= hashArraySize || linearStoredElements > (hashArraySize * 0.7)) {
                done = true;

                // IF THE LOOP DOES NOT NEED TO BE BROKEN ---------------------------------------------------------------

            } else {

                // IF THE POSITION IN THE ARRAY IS ALREADY FILLED (COLLISION) -----------------------------------------

                if (hashArray[position] != null) {
                    position++;
                    linearCollisions++;

                    // IF THE POSITION IN THE ARRAY IS NOT FULL ---------------------------------------------------------------

                } else {
                    hashArray[position] = stringTemp;
                    linearStoredElements++;
                    done = true;
                }
            }

        } while (!done);
    }

    public void quadraticProbing() {

        boolean done;
        int increment = 0;

        do {
            done = false;

            // CONDITIONS TO BREAK THE LOOP ---------------------------------------------------------------

            // ENSURING THAT THE POSITION IS NOT GREATER THAN THE HASH ARRAY SIZE ------------------------

            if (quadraticCheckValue >= hashArraySize || quadraticStoredElements > (hashArraySize * 0.7)) {
                done = true;

                // IF THE LOOP CAN CONTINUE ---------------------------------------------------------------

            } else {

                // IF A COLLISION IS DETECTED ---------------------------------------------------------------

                if (hashArray[quadraticCheckValue] != null) {
                    increment++;
                    quadraticCheckValue = position + (int) Math.pow(increment, 2);
                    quadraticCollisions++;

                    // IF THE POSITION IN THE ARRAY IS NOT FULL (NO COLLISION) ---------------------------------------------------------------

                } else {
                    hashArray[quadraticCheckValue] = stringTemp;
                    quadraticCheckValue = position + (int) Math.pow(increment, 2);
                    quadraticStoredElements++;
                    done = true;
                }
            }

        } while (!done);
    }

    public void linearDisplay() {

        // ENSURING THAT LINEAR PROBING IS POSSIBLE.  IF IT IS NOT, THEN NOTHING WILL DISPLAY ----------------

        if (!linearPossibility) {
            System.out.println(SEPARATOR);
            System.out.println("\t\tCRITICAL ERROR: LINEAR PROBING IS NOT POSSIBLE WITH THIS DATA SET");
            System.out.println("\t\tPlease enter a different string or choose a different probing method");

        } else {

            // CHECKING IF THE WHOLE ARRAY IS EQUAL TO NULL ---------------------------------------------------------------

            // SETTING ALL POSITIONS IN THE ARRAY THAT ARE NULL EQUAL TO ---
            // LINEAR BLANK COUNTER IS INCREMENTED TO SEE HOW MUCH BLANK SPACE THERE IS

            for (int i = 0; i < hashArraySize; i++) {
                if (hashArray[i] == null) {
                    hashArray[i] = "---";
                    linearBlankCounter++;
                }
            }

            // IF LINEAR BLANK DATA = HASH ARRAY SIZE, DATA WILL NOT PRINT SINCE THERE IS NO DATA TO PRINT ---------------------------------------------------------------

            if (linearBlankCounter == hashArraySize) {
                System.out.println(SEPARATOR);
                System.out.println("\t\tERROR: There is nothing in the array.");
                System.out.println("\t\tPlease enter a new string.");
                linearPossibility = false;

            } else {

                // PRINTING THE DATA ---------------------------------------------------------------

                System.out.println(SEPARATOR);
                System.out.println("PRINTING STATISTICS FOR THE LINEAR REHASH");
                System.out.println();

                for (int i = 0; i < hashArraySize; i++) {

                    System.out.printf("%7s", hashArray[i]);

                    // SETTING IT TO PRINT ONLY 20 ITEMS PER LINE --------------------------------------------------

                    if (i % 20 == 19) {
                        System.out.println();
                    }
                }

                // PRINTING THE STATISTICS --------------------------------------------------


                System.out.println("\n");
                System.out.println("Hash Array Size: " + hashArraySize);
                System.out.println("Number of Elements: " + linearStoredElements);
                System.out.println("Remaining Spaces: " + linearBlankCounter);
                System.out.println("Collisions: " + linearCollisions);
                System.out.println("Rehashes: " + linearRehashes);

                // SETTING LINEAR DATA SIZE, WHICH WILL BE USED LATER ON

                linearDataSize = hashArraySize;


            }

        }

        // RESETTING HASH ARRAY SIZE ONLY
        // HASH ARRAY SIZE IS SET TO 0 TO SATISFY THE CHECK IN SETARRAY();
        // LINEAR COLLISIONS CANNOT BE RESET UNTIL LATER ON, SAME WITH LINEAR REHASHES
        // THEY MUST BE COMPARED IN THE ANALYSIS DISPLAY METHOD

        // NOW, RESET HASH ARRAY SIZE

        hashArraySize = 0;
    }

    public void quadraticDisplay() {

        // ENSURING QUADRATIC PROBING IS POSSIBLE.  IF IT IS NOT, NOTHING WILL PRINT  --------------------------------------------------

        if (!quadraticPossibility) {
            System.out.println(SEPARATOR);
            System.out.println("\t\tCRITICAL ERROR: QUADRATIC PROBING IS NOT POSSIBLE WITH THIS DATA SET");
            System.out.println("\t\tPlease enter a different string or choose a different probing method");

        } else {

            // REPLACING ALL AREAS SET TO NULL WITH ---  --------------------------------------------------

            for (int i = 0; i < hashArraySize; i++) {
                if (hashArray[i] == null) {
                    hashArray[i] = "---";
                    quadraticBlankCounter++;
                }
            }

            // IF QUADRATIC BLANK DATA = HASH ARRAY SIZE, DATA WILL NOT PRINT SINCE THERE IS NO DATA TO PRINT  --------------------------------------------------

            if (quadraticBlankCounter == hashArraySize) {
                System.out.println(SEPARATOR);
                System.out.println("\t\tERROR: There is nothing in the array.");
                System.out.println("\t\tPlease enter a new string.");
                quadraticPossibility = false;

            } else {

                // PRINTING THE DATA ----------------------------------------------------------------

                System.out.println(SEPARATOR);
                System.out.println("PRINTING STATISTICS FOR THE QUADRATIC REHASH");
                System.out.println();

                for (int i = 0; i < hashArraySize; i++) {

                    System.out.printf("%7s", hashArray[i]);

                    // SETTING IT TO PRINT ONLY 20 ITEMS PER LINE --------------------------------------------------
                    if (i % 20 == 19) {
                        System.out.println();
                    }
                }

                // PRINTING THE STATISTICS  --------------------------------------------------


                System.out.println("\n");
                System.out.println("Hash Array Size: " + hashArraySize);
                System.out.println("Number of Elements: " + quadraticStoredElements);
                System.out.println("Remaining Spaces: " + quadraticBlankCounter);
                System.out.println("Collisions: " + quadraticCollisions);
                System.out.println("Rehashes: " + quadraticRehashes);

                // RESETTING HASH ARRAY SIZE ONLY
                // HASH ARRAY SIZE IS SET TO 0 TO SATISFY THE CHECK IN SETARRAY();
                // LINEAR COLLISIONS CANNOT BE RESET UNTIL LATER ON, SAME WITH LINEAR REHASHES
                // THEY MUST BE COMPARED IN THE ANALYSIS DISPLAY METHOD

                // MAKING A NOTE OF THE HASH ARRAY SIZE, SINCE THAT WILL BE USED LATER ON
                quadraticDataSize = hashArraySize;

            }
        }

        // NOW, RESET HASH ARRAY SIZE

        hashArraySize = 0;

    }

    public void analysis() {

        // PRIORITY OF RECOMMENDATION
        // 1. IF BOTH ARE NOT POSSIBLE, TELL THEM TO ENTER A NEW STRING
        // 2. IF ONE OF THEM IS NOT POSSIBLE, RECOMMEND THE OTHER
        // 3. WHICHEVER PROBING METHOD HAS THE LEAST REHASHES
        // 4. IF THEY ARE EQUAL, WHICHEVER PROBING METHOD HAS THE LEAST COLLISIONS
        // 5. IF BOTH HAVE THE SAME NUMBER OF REHASHES AND COLLISIONS, RECOMMEND EITHER

        System.out.println(SEPARATOR);
        System.out.println("OUR RECOMMENDATION:");

        // CHECKING IF ONE OF LINEAR OR QUADRATIC PROBING IS NOT POSSIBLE
        // IF THIS OCCURS, THEN THE OTHER WILL BE RECOMMENDED
        // IF BOTH ARE NOT POSSIBLE, THE PROGRAM WILL EXIT AND ASK THE USER TO ENTER A NEW STRING (FOR NOW)

        // IF BOTH ARE NOT POSSIBLE -----------------------------------------------------------------

        // CHECKING IF ONE OF LINEAR OR QUADRATIC PROBING IS NOT POSSIBLE
        // IF THIS OCCURS, THEN THE OTHER WILL BE RECOMMENDED
        // IF BOTH ARE NOT POSSIBLE, THE PROGRAM WILL EXIT AND ASK THE USER TO ENTER A NEW STRING (FOR NOW)

        if (!linearPossibility && !quadraticPossibility) {
            System.out.println(HALFSEPARATOR);
            System.out.println("Both linear and quadratic probing won't work with this data set.");
            System.out.println("Please enter a different string.");
            System.out.println(SEPARATOR);
            neitherRecommendation();

            // IF LINEAR IS NOT POSSIBLE -----------------------------------------------------------------

        } else if (!linearPossibility) {
            System.out.println(HALFSEPARATOR);
            System.out.println("In this situation, linear probing cannot store all the data.");
            System.out.println("Therefore, you MUST use quadratic probing to store the data.");
            quadraticRecommendation();

            // IF QUADRATIC IS NOT POSSIBLE -----------------------------------------------------------------

        } else if (!quadraticPossibility) {
            System.out.println(HALFSEPARATOR);
            System.out.println("In this situation, quadratic probing cannot store all the data.");
            System.out.println("Therefore, you MUST use linear probing to store the data.");
            linearRecommendation();

            // IF THEY ARE BOTH POSSIBLE -----------------------------------------------------------------

        } else {

            // IF LINEAR REHASHES ARE GREATER, RECOMMEND QUADRATIC REHASHING
            // THIS NEXT BLOCK OF CODE WILL MAKE THE RECOMMENDATION AND PRINT THE STATISTICS TO THE SCREEN

            if (linearRehashes > quadraticRehashes) {
                System.out.println(HALFSEPARATOR);
                System.out.println("There are more linear rehashes than quadratic rehashes.");
                System.out.println(HALFSEPARATOR);
                System.out.println("Linear Rehashes: " + linearRehashes + "\tQuadratic Rehashes: " + quadraticRehashes);
                System.out.println("Linear Data Size: " + linearDataSize + "\tQuadratic Data Size: " + quadraticDataSize);
                System.out.println(HALFSEPARATOR);
                System.out.println("Therefore, we would recommend using quadratic probing.");
                quadraticRecommendation();

                // IF QUADRATIC REHASHES ARE GREATER, RECOMMEND QUADRATIC REHASHING
                // THIS NEXT BLOCK OF CODE WILL MAKE THE RECOMMENDATION AND PRINT THE STATISTICS TO THE SCREEN

            } else if (linearRehashes < quadraticRehashes) {
                System.out.println(HALFSEPARATOR);
                System.out.println("There are less linear rehashes than quadratic rehashes.");
                System.out.println(HALFSEPARATOR);
                System.out.println("Linear Rehashes: " + linearRehashes + "\tQuadratic Rehashes: " + quadraticRehashes);
                System.out.println("Linear Data Size: " + linearDataSize + "\tQuadratic Data Size: " + quadraticDataSize);
                System.out.println(HALFSEPARATOR);
                System.out.println("Therefore, we would recommend using linear probing.");
                linearRecommendation();

            } else {

                // THIS WILL RUN IF THERE ARE AN EQUAL AMOUNT OF LINEAR AND QUADRATIC REHASHES

                // IF THERE ARE MORE LINEAR COLLISIONS THAN QUADRATIC COLLISIONS, RECOMMEND THAT
                // THIS NEXT BLOCK OF CODE WILL MAKE THE RECOMMENDATION AND PRINT THE STATISTICS TO THE SCREEN

                if (linearCollisions > quadraticCollisions) {
                    System.out.println(HALFSEPARATOR);
                    System.out.println("There are an equal amount of linear and quadratic rehashes,");
                    System.out.println("Which means that the size of the structures are the same.");
                    System.out.println("However, there are more linear collisions than quadratic collisions.");
                    System.out.println(HALFSEPARATOR);
                    System.out.println("Linear Rehashes: " + linearRehashes + "\tQuadratic Rehashes: " + quadraticRehashes);
                    System.out.println("Linear Data Size: " + linearDataSize + "\tQuadratic Data Size: " + quadraticDataSize);
                    System.out.println("Linear Collisions: " + linearCollisions + "\tQuadratic Collisions: " + quadraticCollisions);
                    System.out.println(HALFSEPARATOR);
                    System.out.println("Therefore, we would recommend using quadratic probing.");
                    quadraticRecommendation();

                    // IF THERE ARE MORE LINEAR COLLISIONS THAN QUADRATIC COLLISIONS, RECOMMEND THAT
                    // THIS NEXT BLOCK OF CODE WILL MAKE THE RECOMMENDATION AND PRINT THE STATISTICS TO THE SCREEN

                } else if (linearCollisions < quadraticCollisions) {
                    System.out.println(HALFSEPARATOR);
                    System.out.println("There are an equal amount of linear and quadratic rehashes,");
                    System.out.println("Which means that the size of the structures are the same.");
                    System.out.println("However, there are more quadratic collisions than linear collisions.");
                    System.out.println(HALFSEPARATOR);
                    System.out.println("Linear Rehashes: " + linearRehashes + "\tQuadratic Rehashes: " + quadraticRehashes);
                    System.out.println("Linear Data Size: " + linearDataSize + "\tQuadratic Data Size: " + quadraticDataSize);
                    System.out.println("Linear Collisions: " + linearCollisions + "\tQuadratic Collisions: " + quadraticCollisions);
                    System.out.println(HALFSEPARATOR);
                    System.out.println("Therefore, we would recommend using linear probing.");
                    linearRecommendation();

                } else {

                    // THIS WILL RUN IF COLLISIONS AND REHASHES ARE BOTH EQUAL
                    // THE PROGRAM WILL LEAVE IT UP TO THE USER AND NOT GIVE A CONCRETE RECOMMENDATION, AS IT DOESN'T MATTER
                    // THAT MUCH IN THIS SITUATION

                    System.out.println(HALFSEPARATOR);
                    System.out.println("There are  an equal amount of linear and quadratic rehashes,");
                    System.out.println("as well as an equal amount of linear and quadratic collisions.");
                    System.out.println("Both linear and quadratic probing will store the data in a similar manner.");
                    System.out.println("Therefore, you can choose either probing method in this instance.");
                    bothRecommendation();


                }
            }
        }

        // PRINTING STATISTICS OF RECOMMENDATIONS ------------------------------------------------------
        // THESE RESET EVERY TIME THE PROGRAM IS RUN

        displayStatistics();
    }

    public void linearRecommendation() {

        // SETTING HOW MANY TIMES LINEAR WAS RECOMMENDED, AND THE DATA SIZES FOR THIS -----------------

        if (recommendLinear < MAXSTRINGS) {
            linearRecommendedDataSizes[0] = linearDataSize;
        }
        recommendLinear++;
    }

    public void quadraticRecommendation() {

        // SETTING HOW MANY TIMES LINEAR WAS RECOMMENDED, AND THE DATA SIZES FOR THIS -----------------

        if (recommendQuadratic < MAXSTRINGS) {
            quadraticRecommendedDataSizes[0] = quadraticDataSize;
        }
        recommendQuadratic++;
    }

    public void bothRecommendation() {
        // REALISTICALLY IT WON'T MATTER WHAT IT'S SET TO SINCE THE DATA SIZES ARE THE SAME
        // THEREFORE, WE'LL JUST USE LINEAR

        // SETTING HOW MANY TIMES LINEAR WAS RECOMMENDED, AND THE DATA SIZES FOR THIS -----------------

        if (recommendBoth < MAXSTRINGS) {
            bothRecommendedDataSizes[0] = linearDataSize;
        }
        recommendBoth++;
    }

    public void neitherRecommendation() {
        // SIMILAR SITUATION, IT WILL NOT MATTER WHAT IT'S SET TO SINCE THE DATA SIZES ARE THE SAME
        // THEREFORE, LINEAR WILL BE USED

        // SETTING HOW MANY TIMES LINEAR WAS RECOMMENDED, AND THE DATA SIZES FOR THIS -----------------

        if (recommendNeither < MAXSTRINGS) {
            neitherRecommendedDataSizes[0] = linearDataSize;
        }
        recommendNeither++;
    }

    public void displayStatistics() {

        int statsCounter = 0;

        // SORTING ALL OF THE ARRAYS --------------------------------------------------------------------

        Arrays.sort(linearRecommendedDataSizes);
        Arrays.sort(quadraticRecommendedDataSizes);
        Arrays.sort(bothRecommendedDataSizes);
        Arrays.sort(neitherRecommendedDataSizes);

        // DISPLAYING THE STATISTICS FOR LINEAR --------------------------------------------------------------------

        System.out.println(SEPARATOR);
        System.out.println("STATISTICS OF RECOMMENDATIONS:");
        System.out.println(HALFSEPARATOR);

        // NUMBER OF LINEAR RECOMMENDATIONS ----------------------------------------------------------------------------------------------------------------

        System.out.println("Number of linear recommendations: " + recommendLinear);

        // PRINTING THE DATA SIZES FOR THE LINEAR RECOMMENDATIONS --------------------------------------------------------------------

        // CHECKING IF IT'S IN THE REQUIRED RANGE (CAN ONLY STORE MAX OF 100)

        if (recommendLinear > 0 && recommendLinear <= MAXSTRINGS) {
            System.out.println("Data sizes for linear recommendations:");

            // PRINTING THE DATA SIZES

            for (int i = 0; i < MAXSTRINGS; i++) {

                // IF IT IS NOT INVALID, IT MEANS THERE'S A VALUE THERE

                if (linearRecommendedDataSizes[i] != INVALID) {

                    // PRINTING THE DATA SIZE AND INCREMENTING THE STATISTICS COUNTER

                    System.out.print(linearRecommendedDataSizes[i] + ", ");
                    statsCounter++;

                    // MODULO OPERATION FOR NEW LINE

                    if (statsCounter % 20 == 19) {
                        System.out.println();
                    }
                }
            }
            System.out.println();

            // IF THERE HAVE BEEN NO LINEAR RECOMMENDATIONS --------------------------------------------------------------------


        } else if (recommendLinear == 0) {
            System.out.println("No linear recommendations have been made, so no data sizes can be computed.");

            // IF MORE THAN 100 STRINGS HAVE BEEN REACHED --------------------------------------------------------------------

        } else {
            System.out.println("\t\tERROR: Maximum number of strings that can be documented has been reached.");
            System.out.println("\t\tPlease reset the statistics in the main menu.");
        }

        System.out.println(HALFSEPARATOR);

        statsCounter = 0;

        // DISPLAYING THE STATISTICS FOR QUADRATIC --------------------------------------------------------------------


        // NUMBER OF QUADRATIC RECOMMENDATIONS ----------------------------------------------------------------------------------------------------------------

        System.out.println("Number of quadratic recommendations: " + recommendQuadratic);

        // PRINTING THE DATA SIZES  --------------------------------------------------------------------

        // CHECKING IF IT'S IN THE REQUIRED RANGE (CAN ONLY STORE MAX OF 100)

        if (recommendQuadratic > 0 && recommendQuadratic <= MAXSTRINGS) {
            System.out.println("Data sizes for quadratic recommendations:");

            // PRINTING THE DATA SIZES

            for (int i = 0; i < MAXSTRINGS; i++) {

                // IF IT IS NOT INVALID, IT MEANS THERE'S A VALUE THERE

                if (quadraticRecommendedDataSizes[i] != INVALID) {

                    // PRINTING THE DATA SIZE AND INCREMENTING THE STATISTICS COUNTER

                    System.out.print(quadraticRecommendedDataSizes[i] + ", ");
                    statsCounter++;

                    // MODULO OPERATION FOR NEW LINE

                    if (statsCounter % 20 == 19) {
                        System.out.println();
                    }
                }
            }
            System.out.println();

            // IF THERE HAVE BEEN NO RECOMMENDATIONS --------------------------------------------------------------------


        } else if (recommendQuadratic == 0) {
            System.out.println("No quadratic recommendations have been made, so no data sizes can be computed.");

            // IF MORE THAN 100 STRINGS HAVE BEEN REACHED --------------------------------------------------------------------

        } else {
            System.out.println("\t\tERROR: Maximum number of strings that can be documented has been reached.");
            System.out.println("\t\tPlease reset the statistics in the main menu.");
        }

        System.out.println(HALFSEPARATOR);

        statsCounter = 0;

        // DISPLAYING THE STATISTICS FOR BOTH (EITHER OR CAN BE USED) --------------------------------------------------------------------

        // NUMBER OF BOTH RECOMMENDATIONS ----------------------------------------------------------------------------------------------------------------

        System.out.println("Number of either/or recommendations: " + recommendBoth);

        // PRINTING THE DATA SIZES  --------------------------------------------------------------------

        // CHECKING IF IT'S IN THE REQUIRED RANGE (CAN ONLY STORE MAX OF 100)

        if (recommendBoth > 0 && recommendBoth <= MAXSTRINGS) {
            System.out.println("Data sizes for both recommendations (when either probing method can be used):");

            // PRINTING THE DATA SIZES

            for (int i = 0; i < MAXSTRINGS; i++) {

                // IF IT IS NOT INVALID, IT MEANS THERE'S A VALUE THERE

                if (bothRecommendedDataSizes[i] != INVALID) {

                    // PRINTING THE DATA SIZE AND INCREMENTING THE STATISTICS COUNTER

                    System.out.print(bothRecommendedDataSizes[i] + ", ");
                    statsCounter++;

                    // MODULO OPERATION FOR NEW LINE

                    if (statsCounter % 20 == 19) {
                        System.out.println();
                    }
                }
            }
            System.out.println();

            // IF THERE HAVE BEEN NO RECOMMENDATIONS --------------------------------------------------------------------


        } else if (recommendBoth == 0) {
            System.out.println("No recommendations for both probing methods have been made, so no data sizes can be computed.");

            // IF MORE THAN 100 STRINGS HAVE BEEN REACHED --------------------------------------------------------------------

        } else {
            System.out.println("\t\tERROR: Maximum number of strings that can be documented has been reached.");
            System.out.println("\t\tPlease reset the statistics in the main menu.");
        }

        System.out.println(HALFSEPARATOR);

        statsCounter = 0;

        // DISPLAYING THE STATISTICS FOR NEITHER --------------------------------------------------------------------

        // NUMBER OF NEITHER RECOMMENDATIONS ----------------------------------------------------------------------------------------------------------------

        System.out.println("Number of neither recommendations: " + recommendNeither);

        // PRINTING THE DATA SIZES  --------------------------------------------------------------------

        // CHECKING IF IT'S IN THE REQUIRED RANGE (CAN ONLY STORE MAX OF 100)

        if (recommendNeither > 0 && recommendNeither <= MAXSTRINGS) {
            System.out.println("Data sizes when neither probing method is recommended:");

            // PRINTING THE DATA SIZES

            for (int i = 0; i < MAXSTRINGS; i++) {

                // IF IT IS NOT INVALID, IT MEANS THERE'S A VALUE THERE

                if (neitherRecommendedDataSizes[i] != INVALID) {

                    // PRINTING THE DATA SIZE AND INCREMENTING THE STATISTICS COUNTER

                    System.out.print(neitherRecommendedDataSizes[i] + ", ");
                    statsCounter++;

                    // MODULO OPERATION FOR NEW LINE

                    if (statsCounter % 20 == 19) {
                        System.out.println();
                    }
                }
            }
            System.out.println();

            // IF THERE HAVE BEEN RECOMMENDATIONS --------------------------------------------------------------------


        } else if (recommendNeither == 0) {
            System.out.println("No recommendations for neither probing method have been made, so no data sizes can be computed.");

            // IF MORE THAN 100 STRINGS HAVE BEEN REACHED --------------------------------------------------------------------

        } else {
            System.out.println("\t\tERROR: Maximum number of strings that can be documented has been reached.");
            System.out.println("\t\tPlease reset the statistics in the main menu.");
        }

        System.out.println(HALFSEPARATOR);

    }
} // END HASHING CLASS
