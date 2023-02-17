import java.util.ArrayList;
import java.util.Scanner;

public class onlyDouble{
    public static void main(String[] args) {
        ArrayList<String> notationList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Input Notation: ");
        String notation = sc.next();


        //separate numbers from operations
        for(int i=0;i<notation.length();i++){
            String doubleDigit = "";
            String decimalDigit = "";
            boolean dig = true;
            if(!Character.isDigit(notation.charAt(i))){
                notationList.add(String.valueOf(notation.charAt(i)));
            }else {
                //allows to add double-digit
                try{
                    if(Character.isDigit(notation.charAt(i))&&Character.isDigit(notation.charAt(i+2))&&notation.charAt(i+1)=='.'){
                        decimalDigit += notation.charAt(i);
                        decimalDigit  += notation.charAt(i+1);
                        decimalDigit  += notation.charAt(i+2);
                        notationList.add(decimalDigit);
                        i+=2;//skips adding to list
                    }else if(Character.isDigit(notation.charAt(i))&&Character.isDigit(notation.charAt(i+1))){
                        doubleDigit += notation.charAt(i);
                        doubleDigit  += notation.charAt(i+1);
                        notationList.add(doubleDigit);
                        i++;//skips adding to list
                    }
                    else {
                        dig = false;
                    }
                }catch (Exception ignored){
                    notationList.add(String.valueOf(notation.charAt(i)));//catches index bound error and adds single digit to list
                }
                if(!dig){
                    notationList.add(String.valueOf(notation.charAt(i)));
                }


            }
        }

        System.out.println(notationList);// prints out separated list



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

        System.out.println(notationList);//prints out remaining elements in list


    }

    //call Exponent Function
    public static ArrayList<String> myPow(ArrayList<String> notationList){
        int div = notationList.indexOf("^");
        double base = Double.parseDouble(notationList.get(div-1));
        double exponent = Double.parseDouble(notationList.get(div+1));
        String c = String.format("%.2f",Math.pow(base,exponent));
        notationList.add(div,c);
        notationList.remove("^");
        notationList.remove(div+1);
        notationList.remove(div-1);
        return notationList;
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
