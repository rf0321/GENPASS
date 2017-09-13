package genpass;
import java.io.*;
import java.io.PrintWriter;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class GenPass {
    private static final String usagecommand = "genpass";
    private static final String gencommand = "genpass g password";
    private static final String gencommandNumberMode = "genpass g -number password";
    private static final String gencommandMixMode = "genpass g -mix password";

    private static Random random=new Random();

    private String resultdata;

    private static String alphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    private static String numbers = new String("0123456789");

    private String mixingSystem(int charlength,String useChar)  {
        return IntStream.generate(random::nextInt)
                .limit(charlength)                       //IntStream.rangeではなくlimitにする
                .map(Math::abs)                          //int iの絶対値をだす
                .map(n -> n % useChar.length())
                .map(useChar::charAt)                    //n番目の文字の抜き出し
                .mapToObj(c -> String.valueOf((char) c)) //String型に置き換え
                .collect(Collectors.joining());          //ランダム要素を詰め込む。
    }
    private String randomAlpha(){
        return mixingSystem(20,alphabet);
    }
    private String randomNum() {
        return mixingSystem(20,numbers);
    }
    private String randomMix() {
        return mixingSystem(20,alphabet+numbers);
    }
    private void showUsage() throws Exception {
        System.out.println("usage:passgen");
        System.out.println("How to use");
        System.out.println("generate alphabet-type: genpass g password");
        System.out.println("generate number-type: genpass g -number password");
        System.out.println("generate mix-type: genpass g -mix password");
        generate();
    }
    public void generate() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        if (s.equals(usagecommand)){
            showUsage();
        } else if (s.equals(gencommand)){
            resultdata=randomAlpha();
        } else if (s.equals(gencommandNumberMode)){
            resultdata=randomNum();
        } else if (s.equals(gencommandMixMode)){
            resultdata=randomMix();
        } else {
            System.out.println(s + " is not command.Please check usage command 'passgen'");
            System.exit(0);
        }
        System.out.println(resultdata);
        saveToCSVfile(resultdata);
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
            System.out.println("Generated password was saved on CSV file.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
