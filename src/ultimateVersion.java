import java.util.ArrayList;
import java.util.Scanner;

public class ultimateVersion{
    //now Allows any number of digits and decimal places
    //multiple selected operation and numbers
    //Implements methods, condition, loops, arraylist

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input Notation: ");
        String notation = sc.next();

        ArrayList<String> notationList = myNotationList(notation);
        System.out.println("Notation: "+notationList);

        ArrayList<String> resultList = myResultNotation(notationList);
        System.out.println("Answer: "+resultList);



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
                isDigit += notation.charAt(i);//adds digits until operation exist
                if(i==(notation.length()-1)){
                    notationList.add(isDigit);//adds last digits to list
                }
            }else {
                notationList.add(isDigit);//add isdigit to list
                notationList.add(String.valueOf(notation.charAt(i)));//adds operation
                isDigit = "";//resets value of isDigit
            }


        }
        return notationList;// returns separated number and operators
    }

    //call Exponent Function
    public static ArrayList<String> myPow(ArrayList<String> notationList){//[x,^,y]
        int div = notationList.indexOf("^");//gets index of ^: 1
        double base = Double.parseDouble(notationList.get(div-1)); //parse numbers index of list [1-1]||[0]: x
        double exponent = Double.parseDouble(notationList.get(div+1));//parse numbers index of list [1+1]||[2]: y
        String c = String.format("%.2f",Math.pow(base,exponent));// gets pow of base and exponent: z
        notationList.add(div,c);//[x,z,/,y]
        notationList.remove("^");//[x,z,y]
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

