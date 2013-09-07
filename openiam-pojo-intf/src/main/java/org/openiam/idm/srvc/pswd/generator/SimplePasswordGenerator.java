package org.openiam.idm.srvc.pswd.generator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: suneetshah
 * Date: 9/6/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimplePasswordGenerator implements PasswordGenerator {

    static List<String> threeCharWords = new LinkedList<String>();
    static Random rand = new Random(System.currentTimeMillis());


    static {
        buildPassword();
    }

    public SimplePasswordGenerator() {
    }


    /**
     * Generates a simple password based on a three character word and a number
     * @param size - number of digits to include after the 3 character word
     * @return
     */
    public String generatePassword(int size) {

            int wordIndx = getRandomWord();
            String newPassword = threeCharWords.get(wordIndx);
            return newPassword +  getRandomNumber(size);


    }

    @Override
    public String generatePassword(PasswordGenParameters param) {
        return generatePassword(param.size);
    }

    private static int getRandomWord() {

        return rand.nextInt(140 - 1) + 1;

    }

    private static String getRandomNumber(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<size; i++) {
            builder.append(rand.nextInt(10));

        }
        return builder.toString();

    }

    private static void buildPassword() {

        threeCharWords.add("act");
        threeCharWords.add("add");
        threeCharWords.add("age");
        threeCharWords.add("air");
        threeCharWords.add("all");
        threeCharWords.add("and");
        threeCharWords.add("ant");
        threeCharWords.add("any");
        threeCharWords.add("ape");
        threeCharWords.add("arm");
        threeCharWords.add("art");
        threeCharWords.add("ask");
        threeCharWords.add("bag");
        threeCharWords.add("bat");
        threeCharWords.add("bed");
        threeCharWords.add("bee");
        threeCharWords.add("big");
        threeCharWords.add("box");
        threeCharWords.add("boy");
        threeCharWords.add("bug");
        threeCharWords.add("bus");
        threeCharWords.add("cab");
        threeCharWords.add("can");
        threeCharWords.add("cap");
        threeCharWords.add("car");
        threeCharWords.add("cat");
        threeCharWords.add("cow");
        threeCharWords.add("cub");
        threeCharWords.add("cup");
        threeCharWords.add("cut");
        threeCharWords.add("dad");
        threeCharWords.add("day");
        threeCharWords.add("dig");
        threeCharWords.add("dog");
        threeCharWords.add("dot");
        threeCharWords.add("dry");
        threeCharWords.add("dug");
        threeCharWords.add("ear");
        threeCharWords.add("eat");
        threeCharWords.add("eel");
        threeCharWords.add("egg");
        threeCharWords.add("elk");
        threeCharWords.add("elm");
        threeCharWords.add("end");
        threeCharWords.add("eye");
        threeCharWords.add("fan");
        threeCharWords.add("far");
        threeCharWords.add("fed");
        threeCharWords.add("fig");
        threeCharWords.add("fir");
        threeCharWords.add("fix");
        threeCharWords.add("fly");
        threeCharWords.add("fog");
        threeCharWords.add("fox");
        threeCharWords.add("fry");
        threeCharWords.add("fun");
        threeCharWords.add("fur");
        threeCharWords.add("gal");
        threeCharWords.add("get");
        threeCharWords.add("got");
        threeCharWords.add("gum");
        threeCharWords.add("guy");
        threeCharWords.add("had");
        threeCharWords.add("ham");
        threeCharWords.add("hat");
        threeCharWords.add("hay");
        threeCharWords.add("hen");
        threeCharWords.add("her");
        threeCharWords.add("hid");
        threeCharWords.add("him");
        threeCharWords.add("hip");
        threeCharWords.add("his");
        threeCharWords.add("hop");
        threeCharWords.add("ink");
        threeCharWords.add("jam");
        threeCharWords.add("jar");
        threeCharWords.add("jet");
        threeCharWords.add("kid");
        threeCharWords.add("leg");
        threeCharWords.add("lid");
        threeCharWords.add("lip");
        threeCharWords.add("log");
        threeCharWords.add("low");
        threeCharWords.add("mad");
        threeCharWords.add("man");
        threeCharWords.add("map");
        threeCharWords.add("mat");
        threeCharWords.add("may");
        threeCharWords.add("mom");
        threeCharWords.add("moo");
        threeCharWords.add("mop");
        threeCharWords.add("mud");
        threeCharWords.add("nap");
        threeCharWords.add("net");
        threeCharWords.add("new");
        threeCharWords.add("nod");
        threeCharWords.add("not");
        threeCharWords.add("nut");
        threeCharWords.add("oar");
        threeCharWords.add("oat");
        threeCharWords.add("off");
        threeCharWords.add("old");
        threeCharWords.add("owl");
        threeCharWords.add("own");
        threeCharWords.add("pad");
        threeCharWords.add("pal");
        threeCharWords.add("pan");
        threeCharWords.add("pat");
        threeCharWords.add("peg");
        threeCharWords.add("pen");
        threeCharWords.add("pet");
        threeCharWords.add("pie");
        threeCharWords.add("pin");
        threeCharWords.add("pop");
        threeCharWords.add("put");
        threeCharWords.add("ran");
        threeCharWords.add("red");
        threeCharWords.add("row");
        threeCharWords.add("rug");
        threeCharWords.add("run");
        threeCharWords.add("sat");
        threeCharWords.add("saw");
        threeCharWords.add("say");
        threeCharWords.add("see");
        threeCharWords.add("set");
        threeCharWords.add("she");
        threeCharWords.add("sip");
        threeCharWords.add("sit");
        threeCharWords.add("sky");
        threeCharWords.add("sun");
        threeCharWords.add("tag");
        threeCharWords.add("tan");
        threeCharWords.add("tap");
        threeCharWords.add("the");
        threeCharWords.add("tip");
        threeCharWords.add("toe");
        threeCharWords.add("top");
        threeCharWords.add("toy");
        threeCharWords.add("try");
        threeCharWords.add("van");
        threeCharWords.add("was");
        threeCharWords.add("web");
        threeCharWords.add("wet");
        threeCharWords.add("win");
        threeCharWords.add("wow");
        threeCharWords.add("yes");
        threeCharWords.add("you");
        threeCharWords.add("zap");
        threeCharWords.add("zip");
        threeCharWords.add("zoo");

    }


}