package com.pvo.components;



public class NumberToWord {
	
	  static String string;
	  static String st1[] = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven","Eight", "Nine", };
	  static String st2[] = { "Hundred", "K", "L", "Cr" };
	  static String st3[] = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen","Fifteen", "Sixteen", "Seventeen", "Eighteen", "Ninteen", };
	  static String st4[] = { "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy","Eighty", "Ninety" };

	  public static String numberToWord(String numb) {
		  	if(numb.lastIndexOf(".") != -1) {
			  	String answer = (String) numb.subSequence(0,numb.lastIndexOf("."));
			    int number = Integer.parseInt(answer);
			  	int n = 1;
			    int word;
			    string = "";
			    while (number != 0) {
			      switch (n) {
			        case 1:
			          word = number % 100;
			          pass(word);
			          if (number > 100 && number % 100 != 0) {
			            show("and ");
			          }
			          number /= 100;
			          break;
			        case 2:
			          word = number % 10;
			          if (word != 0) {
			            show(" ");
			            show(st2[0]);
			            show(" ");
			            pass(word);
			          }
			          number /= 10;
			          break;
			        case 3:
			          word = number % 100;
			          if (word != 0) {
			            show(" ");
			            show(st2[1]);
			            show(" ");
			            pass(word);
			          }
			          number /= 100;
			          break;
			        case 4:
			          word = number % 100;
			          if (word != 0) {
			            show(" ");
			            show(st2[2]);
			            show(" ");
			            pass(word);
			          }
			          number /= 100;
			          break;
			        case 5:
			          word = number % 100;
			          if (word != 0) {
			            show(" ");
			            show(st2[3]);
			            show(" ");
			            pass(word);
			          }
			          number /= 100;
			          break;
			        }
			        n++;
			      }
			      return string;
		  	}
		  	return "";
	}
	  
	  
	  public static void pass(int number) {
		    int word, q;
		    if (number < 10) {
		      show(st1[number]);
		    }
		    if (number > 9 && number < 20) {
		      show(st3[number - 10]);
		    }
		    if (number > 19) {
		      word = number % 10;
		      if (word == 0) {
		        q = number / 10;
		        show(st4[q - 2]);
		      } else {
		        q = number / 10;
		        show(st1[word]);
		        show(" ");
		        show(st4[q - 2]);
		      }
		    }
		  }
	  
	  
	  
	  public static void show(String s) {
		    String st;
		    st = string;
		    string = s;
		    string += st;
		  }
	  
	  
	
	  
	

	/*public enum hundreds {OneHundred, TwoHundred, ThreeHundred, FourHundred, FiveHundred, SixHundred, SevenHundred, EightHundred, NineHundred}
    public enum tens {Twenty, Thirty, Forty, Fifty, Sixty, Seventy, Eighty, Ninety}
    public enum ones {One, Two, Three, Four, Five, Six, Seven, Eight, Nine}
    public enum denom {Thousand, Lakhs, Crores}
    public enum splNums { Ten, Eleven, Twelve, Thirteen, Fourteen, Fifteen, Sixteen, Seventeen, Eighteen, Nineteen}
    public static String text = "";

    public static String numberToWord(String price) 
    {
    	text = "";
    	String answer = (String) price.subSequence(0,price.lastIndexOf("."));
		Long num = Long.parseLong(answer);
        
		int rem = 0;
        int i = 0;
        while(num > 0)
        {
            if(i == 0){
                rem = (int) (num % 1000);
                printText(rem);
                num = num / 1000;
                i++;
            }
            else if(num > 0)
            {
                rem = (int) (num % 100);
                if(rem > 0)
                    text = denom.values()[i - 1]+ " " + text;
                printText(rem);
                num = num / 100;
                i++;
            }
        }
       
		
        return text;
    }

    public static void printText(int num)
    {
        if(!(num > 9 && num < 19))
        {
            if(num % 10 > 0)
                getOnes(num % 10);

            num = num / 10;
            if(num % 10 > 0)
                getTens(num % 10);

            num = num / 10;
            if(num > 0)
                getHundreds(num);
        }
        else
        {
            getSplNums(num % 10);
        }
    }

    public static void getSplNums(int num)
    {
        text = splNums.values()[num]+ " " + text;
    }

    public static void getHundreds(int num)
    {
        text = hundreds.values()[num - 1]+ " " + text;
    }

    public static void getTens(int num)
    {
        text = tens.values()[num - 2]+ " " + text;
    }

    
    public static void getOnes(int num)
    {
        text = ones.values()[num - 1]+ " " + text;
    }*/

}