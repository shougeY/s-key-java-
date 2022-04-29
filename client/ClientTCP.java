package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    Socket socket;
    protected int max_cnt = 5;
    protected String[] pwd_list = new String[max_cnt];
    protected int prot = 6666;
protected  String flag;

    public String md5(String str) {
        Md5 A = new Md5();

        return A.encrypt(str);
    }

    ;

    public String preprocess(String content) {
        String fiest_str = content.substring(0, 16);
        String last_ste = content.substring(16, 32);
        System.out.println(fiest_str);
        System.out.println(last_ste);
        String t = fiest_str;
        String z = last_ste;
        String result = "";
        System.out.println(t.length());
        String[] charsA = t.split("");
        String[] charsB = z.split("");
        for (int i = 0; i < charsA.length; i++) {
            int a = Integer.valueOf(charsA[i], 16);
            int b = Integer.valueOf(charsB[i], 16);
            String anotherBinary = Integer.toBinaryString(a);
            String thisBinary = Integer.toBinaryString(b);
            if (anotherBinary.length() != 4) {
                for (int j = anotherBinary.length(); j < 4; j++) {
                    anotherBinary = "0" + anotherBinary;

                }
            }
            if (thisBinary.length() != 4) {
                for (int j = thisBinary.length(); j < 4; j++) {
                    thisBinary = "0" + thisBinary;

                }
            }

            for (int k = 0; k < anotherBinary.length(); k++) {
                //如果相同位置数相同，则补0，否则补1
                int zz = k + 1;

                String A = thisBinary.substring(k, zz);
                String B = anotherBinary.substring(k, zz);
                if (A.equals(B)) {
                    result += "0";
                } else {
                    result += "1";
                }
            }

        }

        String resultk = "";
        for (int i = 0; i < result.length(); ) {
            String kkk = result.substring(i, i + 4);
            String aaa = Integer.toHexString(Integer.parseInt(kkk, 2));
            resultk = resultk + aaa;
            i = i + 4;
        }
        System.out.println(result.length());
        System.out.println(result);
        System.out.println(resultk);
        return resultk;
    }

    ;

    public void generate_keys(String S) {
        for (int i = 0; i < max_cnt; i++) {
            Md5 A = new Md5();

            String s1 = A.encrypt(S);
            pwd_list[i] = s1;
            S = s1;
        }

        int left = 0;
        int right = pwd_list.length - 1;
        while (left < right) {
            String tmp = pwd_list[left];
            pwd_list[left] = pwd_list[right];
            pwd_list[right] = tmp;
            left++;
            right--;
        }


    }

    ;

    public  void  setSocket() throws IOException {
        Socket client = new Socket("localhost", this.prot);
        this.socket=client;

    }
    public void dengluyanzheng(String A, String B) throws Exception {
        System.out.println("客户端 发送数据");
        Socket client = this.socket;

        OutputStream os = client.getOutputStream();
        InputStream in = client.getInputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        System.out.print(" 请输入用户名：");
        Scanner input = new Scanner(System.in);
        String username = A;

        System.out.println(username);
        System.out.println(username + '\n');
        bw.write(username + '\n');
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String mess = br.readLine();
        String flag;
        flag = mess;
        System.out.printf(flag);
this.flag=flag;
        if (flag.equals("0")) {
            System.out.println("口令已用完，将自动初始化...\n");
            String seed = br.readLine();
            String s_init = username + seed;
            System.out.println(s_init);
            String md5s = this.md5(s_init);
            System.out.println(md5s);
            String S = this.preprocess(md5s);
            System.out.println(S);
            this.generate_keys(S);
            System.out.format("为您生成了%d个口令，请顺序使用：", (this.max_cnt - 1));
            bw.write(this.pwd_list[0] + '\n');
            bw.flush();
            for (int i = 1; i < this.max_cnt; i++) {
                System.out.format("第%d个口令：%s\n", i, this.pwd_list[i]);
            }
            System.out.println("请输入口令：");
            System.out.println("客户存在但是验证次数超过了");


        } else if (flag.equals("1")) {
            System.out.println("请输入口令：");

            bw.write(B + '\n');
            System.out.println(B);
            bw.flush();
            String verfied = br.readLine();
            System.out.println(verfied);
            if (verfied.equals("1")) {
                this.flag="1";
                System.out.println("口令正确!\n");
            } else {
                this.flag="3";
                System.out.println("口令错误！\n");
            }

        } else if (flag.equals("2")) {

            System.out.println("当前用户名不存在，将进行注册..");
            String seed = br.readLine();
            String s_init = username + seed;
            System.out.println(s_init);
            String md5s = this.md5(s_init);
            System.out.println(md5s);
            String S = this.preprocess(md5s);
            System.out.println(S);
            this.generate_keys(S);
            System.out.format("为您生成了%d个口令，请顺序使用：", (this.max_cnt - 1));
            bw.write(this.pwd_list[0] + '\n');
            bw.flush();
            for (int i = 1; i < this.max_cnt; i++) {
                System.out.format("第%d个口令：%s\n", i, this.pwd_list[i]);
            }
            System.out.println("请输入口令：");

//            bw.write(B + '\n');
//            bw.flush();
//            String verfied = br.readLine();
//            if (verfied.equals("1")) {
//                System.out.println("口令正确!\n");
//            } else {
//                System.out.println("口令错误！\n");
//            }

        }
        System.out.println(mess);
        System.out.println("lkjdljsfljas");



    }

//    public static void main(String[]args)throws Exception{
//        ClientTCP A=new ClientTCP();
//        A.dengluyanzheng();
//    }}
}


