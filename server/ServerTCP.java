package server;

import client.Md5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ServerTCP {
    protected int max_cnt = 5;
    public Map<String, clientpp> m;
    protected int prot = 6666;
    private BufferedReader reader;

    //生成种子
    public String get_seed() {
        Random random = new Random();
        int randNumber = random.nextInt(99999999 - 10000000 + 1) + 10000000;
        String num = Integer.toString(randNumber);
        return num;
    }

    ;


    public static void main(String[] args) throws IOException {

            clientpp c1 = new clientpp();
            System.out.println("服务端启动 , 等待连接 .... ");
            // 1.创建 ServerSocket对象，绑定端口，开始等待连接
            ServerSocket ss = new ServerSocket(6666);
            // 2.接收连接 accept 方法, 返回 socket 对象.
            Socket server = ss.accept();
            System.out.println("连接成功");
            ServerTCP a = new ServerTCP();
            a.m = new HashMap<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()));
//            String mess = br.readLine();
//            System.out.println(mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            while (true) {
                String mess = br.readLine();
                System.out.println(mess);
                if (a.m.containsKey(mess)) {
                    int cnt = a.m.get(mess).a;
                    String pwd = a.m.get(mess).pp;
                    if (cnt >= a.max_cnt) {
                        bw.write("0\n");
                        bw.flush();
                        String c = a.get_seed();
                        bw.write(c + '\n');
                        bw.flush();
                        String firsr_pwd = br.readLine();
                        a.m.get(mess).pp = firsr_pwd;
                        a.m.get(mess).a = 1;


                    } else {
                        bw.write("1\n");
                        bw.flush();
                        String _pwd_recv = br.readLine();
                        System.out.println(_pwd_recv);
                        Md5 B = new Md5();
                        String _pwd = B.encrypt(_pwd_recv);
                        System.out.println(a.m.get(mess).pp);
                        System.out.println(_pwd);
                        System.out.println(a.m.get(mess).a);
                        if (_pwd.equals(a.m.get(mess).pp)) {
                            a.m.get(mess).a = a.m.get(mess).a+1;
                            a.m.get(mess).pp = _pwd_recv;
                            bw.write("1\n");
                            bw.flush();
                            System.out.println("有此用户登录次数减一");
                            System.out.println(a.m.get(mess).a);
                        }
                        else {
                            bw.write("0\n");
                            bw.flush();
                        }

                    }

                } else {
                    bw.write("2" + '\n');
                    bw.flush();
                    String c = a.get_seed();
                    bw.write(c + '\n');
                    bw.flush();
                    String firsr_pwd = br.readLine();
                    System.out.println(firsr_pwd);
                    c1.a = 1;
                    c1.pp = firsr_pwd;
                    a.m.put(mess, c1);
                    System.out.println("注册新用户成功");


//                    String _pwd_recv = br.readLine();
//                    Md5 B = new Md5();
//                    String _pwd = B.encrypt(_pwd_recv);
//                    if (_pwd.equals(a.m.get(mess).pp)) {
//                        a.m.get(mess).a = a.m.get(mess).a + 1;
//                        a.m.get(mess).pp = _pwd_recv;
//                        bw.write("1\n");
//                        bw.flush();
//                    } else {
//                        bw.write("0\n");
//                        bw.flush();
//                    }

                }

            }
        }
    }
