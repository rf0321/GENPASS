package genpass.core;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharMixing {

    private static Random random=new Random();

    private static final int charlength = 20;

    public static String mixing(String useChar)  {
        return IntStream.generate(random::nextInt)
                .limit(charlength)
                .map(Math::abs)
                .map(n -> n % useChar.length())
                .map(useChar::charAt)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}
