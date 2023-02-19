
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class prototypePEMDAS{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String notation;
        //loop
        System.out.print("Input Notation: ");
        notation = sc.next();


        while(notation.contains("(")){
            int open = notation.indexOf("(");
            int close = notation.indexOf(")");
            if(Character.isDigit(notation.charAt(open-1))&&Character.isDigit(notation.charAt(close+1))){
                notation = addChar(notation,'*',open);
                notation = addChar(notation,'*',close+2);
            }else {
                if(Character.isDigit(notation.charAt(open-1))){
                    notation = addChar(notation,'*',open);
                } else if (Character.isDigit(notation.charAt(close+1))) {
                    notation = addChar(notation, '*', close + 1);
                }
            }

            open = notation.indexOf("(");
            close = notation.indexOf(")");
            String myRep = notation.substring(open,close+1);
            String myParent = notation.substring(open,close+1);
            myParent = myParent.replace("(","");
            myParent = myParent.replace(")","");
            ArrayList<String> notationParent;
            notationParent = myNotationList(myParent);
            String resultParent = String.join("",myResultNotation(notationParent));
            notation= notation.replace(myRep,resultParent);
        }
        ArrayList<String> notationList = myNotationList(notation);
        System.out.println("Notation: "+notationList);

        ArrayList<String> resultList = myResultNotation(notationList);
        System.out.println("Answer: "+resultList);



    }

    public static String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }




    //process
    public static void myProcess(String notation){
        ArrayList<String> notationList = myNotationList(notation);
        System.out.println(notationList);//prints separated numbers and operators

        ArrayList<String> resultList = myResultNotation(notationList);//final answer
        String resultString = String.join("",resultList);//list to string
        double resultDouble = Double.parseDouble(resultString);//string to double


        //variables to check if resultDouble is double or integer
        int div = resultString.indexOf(".");//checks index of decimal point exist
        int numOne = Integer.parseInt(String.valueOf(resultString.charAt(div+1)));
        int numTwo = Integer.parseInt(String.valueOf(resultString.charAt(div+2)));


        //prints out double if list contains double else integer
        if(numOne>0||numTwo>0){
            System.out.println("Answer is: "+String.format("%.2f",resultDouble));
        }else {
            System.out.println("Answer is: "+ (int) resultDouble);
        }

    }


    public static ArrayList<String> myResultNotation(ArrayList<String> notationList){
        //continues until list length = 0
        while (notationList.size()>1){
            //in order of operation;
            if(notationList.contains("^")){
                myPow(notationList);

                // Product and Quotient
            }else if(notationList.contains("*")&&notationList.contains("/")){
                //order if two same value operation exist in same notation
                int product = notationList.indexOf("*");
                int quotient = notationList.indexOf("/");
                if(product<quotient){
                    myPro(notationList);
                    myDiv(notationList);
                }else {
                    myDiv(notationList);
                    myPro(notationList);
                }
            } else if (notationList.contains("*")){
                myPro(notationList);
            } else if (notationList.contains("/")) {
                myDiv(notationList);

                // Sum and Difference
            }else if(notationList.contains("+")&&notationList.contains("-")){
                //order if two same value operation exist in same notation
                int sum = notationList.indexOf("+");
                int diff = notationList.indexOf("-");
                if(sum<diff){
                    mySum(notationList);
                    myDiff(notationList);
                }else {
                    myDiff(notationList);
                    mySum(notationList);
                }
            } else if (notationList.contains("+")){
                mySum(notationList);
            } else {
                myDiff(notationList);
            }

        }
        return notationList;//returns out remaining elements in list
    }






    public static ArrayList<String> myNotationList(String notation){
        ArrayList<String> notationList = new ArrayList<>();

        String isDigit = "";
        //separate numbers from operations
        for(int i=0;i<notation.length();i++){
            if(Character.isDigit(notation.charAt(i))||notation.charAt(i)=='.'){
                isDigit += notation.charAt(i);// concatenate a char to isDigit
                if(i==(notation.length()-1)){//this adds last number in notation;
                    notationList.add(isDigit);
                }
            }else {
                if(notation.charAt(0)=='-'&&i==0){//checks if notation starts with a negative
                    isDigit += "-";//assigns a negative sign to isDigit
                }else{
                    notationList.add(isDigit);//add isDigit to list
                    isDigit = "";//resets the value of isDigit
                    if(notation.charAt(i)=='-'&&notation.charAt(i-1)=='-'){
                        isDigit += "-";
                    }else{
                        notationList.add(String.valueOf(notation.charAt(i)));//add operator to the list
                    }
                    notationList.remove("");// remove blank index in list(helps if notation starts with negative)
                }
            }
        }

        //returns a separated numbers and operators
        return notationList;
    }





    //call Exponent Function
    public static ArrayList<String> myPow(ArrayList<String> notationList){//[x,^,y]
        int occurrences = Collections.frequency(notationList,"^");
        int div;
        if(occurrences>1){
            div = notationList.lastIndexOf("^");//gets index of ^: 1
        }else{
            div = notationList.indexOf("^");
        }
        double base = Double.parseDouble(notationList.get(div-1)); //parse numbers index of list [1-1]||[0]: x
        double exponent = Double.parseDouble(notationList.get(div+1));//parse numbers index of list [1+1]||[2]: y
        String c = String.format("%.2f",Math.pow(base,exponent));// gets pow of base and exponent: z
        notationList.add(div,c);//[x,z,/,y]
        notationList.remove(div+1);//[x,z,y]
        notationList.remove(div+1);//[x,z]
        notationList.remove(div-1);//[z]
        return notationList;//returns parameter list to an updated list
        //repeats in other operation
    }

    //call Division Function
    public static ArrayList<String> myDiv(ArrayList<String> notationList){
        int div = notationList.indexOf("/");
        double a = Double.parseDouble(notationList.get(div-1));
        double b = Double.parseDouble(notationList.get(div+1));
        String c = String.format("%.2f", a/b);
        notationList.add(div,c);
        notationList.remove("/");
        notationList.remove(div+1);
        notationList.remove(div-1);
        return notationList;
    }

    //call Multiplication Function
    public static ArrayList<String> myPro(ArrayList<String> notationList){
        int div = notationList.indexOf("*");
        double a = Double.parseDouble(notationList.get(div-1));
        double b = Double.parseDouble(notationList.get(div+1));
        String c = String.format("%.2f", a*b);
        notationList.add(div,c);
        notationList.remove("*");
        notationList.remove(div+1);
        notationList.remove(div-1);
        return notationList;
    }

    //call Addition Function
    public static ArrayList<String> mySum(ArrayList<String> notationList){
        int div = notationList.indexOf("+");
        double a = Double.parseDouble(notationList.get(div-1));
        double b = Double.parseDouble(notationList.get(div+1));
        String c = String.format("%.2f", a +b);
        notationList.add(div,c);
        notationList.remove("+");
        notationList.remove(div+1);
        notationList.remove(div-1);
        return notationList;
    }

    //call Subtraction Function
    public static ArrayList<String> myDiff(ArrayList<String> notationList){
        int div = notationList.indexOf("-");
        double a = Double.parseDouble(notationList.get(div-1));
        double b = Double.parseDouble(notationList.get(div+1));
        String c = String.format("%.2f", a -b);
        notationList.add(div,c);
        notationList.remove("-");
        notationList.remove(div+1);
        notationList.remove(div-1);
        return notationList;
    }
}