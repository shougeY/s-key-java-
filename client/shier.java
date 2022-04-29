package client;

import java.io.IOException;
import java.text.ParseException;
public class shier {
    public static void main(String[] args) throws IOException {
        String t="15b6e29e2d69a7e7";
        String z="683255b4c6dcc200";
        String result= "";
        System.out.println(t.length());
        String [] charsA = t.split("");
        String[] charsB=z.split("");
        for(int i=0;i<charsA.length;i++){
            int a= Integer.valueOf(charsA[i],16);
            int b= Integer.valueOf(charsB[i],16);
            String anotherBinary=Integer.toBinaryString(a);
            String thisBinary=Integer.toBinaryString(b);
            if(anotherBinary.length() != 4){
                for (int j = anotherBinary.length(); j<4; j++) {
                    anotherBinary = "0"+anotherBinary;
                    System.out.println(anotherBinary);
                }
            }
            if(thisBinary.length() != 4){
                for (int j = thisBinary.length(); j <4; j++) {
                    thisBinary = "0"+thisBinary;
                    System.out.println(thisBinary);
                }
            }

            for (int k=0;k<anotherBinary.length();k++){
                //如果相同位置数相同，则补0，否则补1
                int zz=k+1;

                String A=thisBinary.substring(k,zz);
                String B=anotherBinary.substring(k,zz);
                if(A.equals(B))
                { result+="0"; }
                else{
                    result+="1";
                }
            }

        }

        String resultk="";
        for (int i=0;i<result.length();){
            String kkk =result.substring(i,i+4);
            String aaa=Integer.toHexString(Integer.parseInt(kkk, 2));
            resultk=resultk+aaa;
            i=i+4;
        }
        System.out.println(result.length());
        System.out.println(result);
        System.out.println(resultk);
        }
    }

