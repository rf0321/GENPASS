package genpass;
import genpass.core.CharComponet;
import genpass.core.CharMixing;
import java.io.*;
import java.io.PrintWriter;


public class CommandCaller {
    private String resultdata;

    private String randomAlpha(){
        return CharMixing.mixing(CharComponet.ALPHABET);
    }
    private String randomNum() {
        return CharMixing.mixing(CharComponet.NUMBER);
    }
    private String randomMix() {
        return CharMixing.mixing(CharComponet.NUMBER+CharComponet.ALPHABET);
    }
    private void showhelp(){
        System.out.println("usage:passgen");
        System.out.println("===============How to use================");
        System.out.println("<Generate alphabet-type>:genpass g password");
        System.out.println("<Generate number-type>:genpass g -number password");
        System.out.println("<Generate mix-type>:genpass g -mix password");
    }
    public void generate(){
        System.out.print("genpass>");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();

            if(s.isEmpty()){
                generate();
            }
            switch (s){
                default:
                    System.out.println(s + " is not command.Please check usage command 'genpass'");
                    System.exit(0);
                case CharComponet.exit_command:
                    System.exit(0);
                case CharComponet.helpercommand:
                    showhelp();
                    generate();
                case CharComponet.standard:
                    resultdata = randomAlpha();
                case CharComponet.number_mode:
                    resultdata = randomNum();
                case CharComponet.mix_mode:
                    resultdata = randomMix();
            }
            System.out.println(resultdata);
            saveToCSVfile(resultdata);
        }catch (Exception e){
        }
    }
    private void saveToCSVfile(String data) throws Exception {
        try {
            System.out.println("You could generate password.Please set the name of password");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String filename = br.readLine();
            FileWriter fw = new FileWriter(filename + ".csv", false);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fw));

            printWriter.print(filename + ":" + data);
            printWriter.println();
            printWriter.close(); //ファイルへ実行結果を書き出す
            System.out.println("Generated password is saved on CSV file.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
