package NumSysConverter;

import java.util.Scanner;

class NumSysConverter {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        NumSys startSys, targetSys;
        boolean jobDone = false;
        do {
            Input inp = readInput();
            try {
                String num = inp.x;
                startSys = getNumSysObject(inp.startSys);
                targetSys = getNumSysObject(inp.targetSys);
                String result = makeConvertation(num, startSys, targetSys);
                showResult(result);
                jobDone = true;
            } catch (ArithmeticException e) {
                System.out.println("Цифры в числе должны быть меньше основания исходной СС. Введите заново.");
            } catch (Exception e) {
                System.out.println("Неверное основание системы счисления. Введите заново.");
            }
        } while (!jobDone);
    }

    private static Input readInput() {
        System.out.print("Число (положительное целое): ");
        String x = in.next().toUpperCase();
        System.out.print("Перевод из (2-16 или Ф): ");
        String startSys = in.next().toUpperCase(); 
        System.out.print("Перевод в (2-16 или Ф): ");
        String targetSys = in.next().toUpperCase();
        return new Input(x, startSys, targetSys);
    }

    private static String makeConvertation(String x, NumSys start, NumSys target) {
        String b1 = start.base, b2 = target.base;
        if (b1 == "10" && b2 != "10") {
            return target.convertFromDec(x);
        } else if (b1 != "10" && b2 == "10") {
            return start.convertToDec(x);
        } else {
            String t = start.convertToDec(x);
            return target.convertFromDec(t);
        }
    }

    private static void showResult(String n) {
        System.out.printf("Переведённый результат:\n%s\n", n);
    }

    private static NumSys getNumSysObject(String base) throws Exception {
        if (base.equals("Ф"))
            return new FactNumSys(base);

        return new NumSys(base);
    }
}

class Input {
    String x;
    String startSys;
    String targetSys;    

    public Input(String x, String startSys, String targetSys) {
        this.x = x;
        this.startSys = startSys;
        this.targetSys = targetSys;
    }
}

class NumSys {
    final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    String base;

    public NumSys() {
        this.base = "10";
    }

    public NumSys(String base) throws Exception {
        checkBase(base);
        this.base = base;
    }

    public String convertToDec(String num) {
        checkNumber(num);
        int b = Integer.parseInt(base);
        int result = 0;
        int n = num.length();
        for (int i = n - 1; i >= 0; i--)
            result += getDigitValue(num.charAt(n - 1 - i)) * Math.pow(b, i);
        return String.valueOf(result);
    }

    public String convertFromDec(String num) {
        int b = Integer.parseInt(base);
        int n = Integer.parseInt(num);
        String result = "";
        while (n > 0) {
            int mod = n % b;
            result = getDigitChar(mod) + result;
            n /= b;
        }
        return result;
    }

    protected void checkNumber(String num) {
        int b = Integer.parseInt(base);
        for (int i = 0; i < num.length(); i++) {
            int d = getDigitValue(num.charAt(i)); 
            if (d >= b || d > getDigitValue(DIGITS[DIGITS.length - 1]) || d == -1)
                throw new ArithmeticException();
        }
    }

    protected void checkBase(String base) throws Exception {
        int b = Integer.parseInt(base);
        if (b < 2 || b > 16) 
            throw new Exception();
    }

    protected int getDigitValue(char digit) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (DIGITS[i] == digit) 
                return i;
        }
        return -1;
    }

    protected char getDigitChar(int digit) {
        return DIGITS[digit];
    }
}

class FactNumSys extends NumSys {
    public FactNumSys(String base) throws Exception {
        checkBase(base);
        this.base = base;
    }

    public String convertToDec(String num) {
        int result = 0;
        int n = num.length();
        for (int i = n - 1; i >= 0; i--)
            result += getDigitValue(num.charAt(n - 1 - i)) * fact(i + 1);
        return String.valueOf(result);
    }

    public String convertFromDec(String num) {
        int n = Integer.parseInt(num);
        String result = "";
        int d = 2;
        while (n > 0) {
            int mod = n % d;
            result = getDigitChar(mod) + result;
            n /= d;
            d += 1;
        }
        return result;
    }

    protected void checkNumber(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (getDigitValue(num.charAt(i)) > 9 || getDigitValue(num.charAt(i)) < 0)
                throw new ArithmeticException();
        }
    }

    protected void checkBase(String base) throws Exception {
        if (!base.equals("Ф"))   
            throw new Exception();
    }

    private static int fact(int n) {
        return n == 1 ? 1 : (n * fact(n - 1));
    } 
}
