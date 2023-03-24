
import java.util.ArrayList;
import java.util.Arrays;

class BigInt {

    // Заранее подготовленные константы.
    private static final BigInt ZERO = new BigInt(0);
    private static final BigInt ONE = new BigInt(1);
    private static final BigInt TWO = new BigInt(2);
    private static final BigInt THREE = new BigInt(3);
    private static final BigInt FOUR = new BigInt(4);
    private static final BigInt FIVE = new BigInt(5);
    private static final BigInt SIX = new BigInt(6);
    private static final BigInt SEVEN = new BigInt(7);
    private static final BigInt EIGHT = new BigInt(8);
    private static final BigInt NINE = new BigInt(9);

    private final byte[] dgt;
    private boolean sign;

    private final ArrayList<Integer> digits = new ArrayList();

    public BigInt(String str) { // Конструктор BigInt.

        str = clean(str);

        digits.clear();

        // Работа с сотавляющими числа - цифрами.
        for (int i = 0; i < str.length(); i++) {

            if (i == 0 && str.charAt(i) == '-') {
                continue;
            }

            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                throw new IllegalArgumentException(" Input not allowed " + str);
            }
        }

        if (str.charAt(0) == '-') {

            sign = true;
            dgt = new byte[str.length() - 1];

            int i = 0;

            for (int digitIndex = 0; digitIndex < dgt.length; digitIndex++) {

                if (digitIndex == str.length() - 1) {
                    continue;
                }

                int ch = (str.charAt(str.length() - digitIndex - 1) - 48);
                dgt[i++] = (byte) ch;
            }

        } else {

            dgt = new byte[str.length()];

            for (int digitIndex = 0; digitIndex < dgt.length; digitIndex++) {

                int ch = (str.charAt(str.length() - digitIndex - 1) - 48);
                dgt[digitIndex] = (byte) ch;

                digits.add((int) ch);

            }

        }

    }

    public BigInt(long longNumber) { // Конструктор. создающий BigInt из типа long.
        this(Long.toString(longNumber));
    }

    public BigInt() {
        this(0);
    }

    public static BigInt valueOf(long longNumber) { // Для парсинга из типа long.
        return new BigInt(longNumber);
    }

    public static BigInt fromString(String stringNumber) { // Для парсинга из типа string.
        return new BigInt(stringNumber);
    }

    public static String clean(String str) { // Очистка буфера - строки.

        str = str.trim();

        if (str.length() == 0) {
            throw new IllegalArgumentException("");
        }

        int zeroCount = 0;

        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) == '0') {
                zeroCount++;
            }

        }

        if (zeroCount == str.length()) {
            return "0";
        }

        StringBuilder digits = new StringBuilder(str.length());

        boolean isLeadingZero = true;

        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) != '0') {
                isLeadingZero = false;
            }

            if (isLeadingZero) {
                continue;
            }

            digits.append(str.charAt(i));
        }

        return digits.toString();
    }

    public String toString() { // Преобразование в строку.
        StringBuilder strBuilder = new StringBuilder(dgt.length);

        for (byte digit : dgt) {
            strBuilder.append(digit);
        }

        if (sign) {
            strBuilder.append('-');
        }

        return strBuilder.reverse().toString();
    }

    private String toStringWithoutDash() { // Преобразование в строку без разделителей.

        StringBuilder strBuilder = new StringBuilder(dgt.length);

        for (byte digit : dgt) {
            strBuilder.append(digit);
        }

        return strBuilder.reverse().toString();
    }

    public int length() {
        return dgt.length;
    }

    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BigInt number = (BigInt) object;

        if (sign != number.sign) {
            return false;
        }

        return Arrays.equals(dgt, number.dgt);
    }

    public boolean isGreaterThan(BigInt secondNumber) { // Больше ли, чем то число, которое передали в метод.

        if (sign && secondNumber.sign) {

            if (length() > secondNumber.length()) {
                return false;
            }

            if (length() < secondNumber.length()) {
                return true;
            }

            for (int i = dgt.length - 1; i >= 0; i--) {

                if (dgt[i] > secondNumber.dgt[i]) {
                    return false;
                }

                if (dgt[i] < secondNumber.dgt[i]) {
                    return true;
                }

            }

        }

        if (sign && !secondNumber.sign) {
            return false;
        }

        if (!sign && secondNumber.sign) {
            return true;
        }

        if (!sign) {
            return isGreaterThanWithoutSign(secondNumber);
        }

        return false;
    }

    public boolean isGreaterThanWithoutSign(BigInt secondNumber) {
        if (length() > secondNumber.length()) {
            return true;
        }
        if (length() < secondNumber.length()) {
            return false;
        }

        for (int i = dgt.length - 1; i >= 0; i--) {
            if (dgt[i] > secondNumber.dgt[i]) {
                return true;
            }
            if (dgt[i] < secondNumber.dgt[i]) {
                return false;
            }
        }

        return false;
    }

    private static int compareStringBuilder(StringBuilder str1, StringBuilder str2) {
        String str3 = str1.toString();
        String str4 = str2.toString();

        str3 = clean(str3);
        str4 = clean(str4);

        str1.delete(0, str1.length());
        str2.delete(0, str2.length());

        str1.append(str3);
        str2.append(str4);

        if (str1.length() > str2.length()) {
            return 1;
        }
        if (str2.length() > str1.length()) {
            return -1;
        }

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return 1;
            }
            if (str2.charAt(i) > str1.charAt(i)) {
                return -1;
            }
        }

        return 0;
    }

    private boolean compareArray(byte[] a, byte[] b) {
        if (a.length > b.length) {
            return true;
        }
        if (a.length < b.length) {
            return false;
        }

        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] > b[i]) {
                return true;
            }
            if (a[i] < b[i]) {
                return false;
            }
        }

        return true;
    }

    private ArrayList<BigInt> sieve(BigInt number) {
        int squareRootOfNumber = (int) Math.sqrt(Long.parseLong(number.toString()));

        boolean[] isNotPrime = new boolean[squareRootOfNumber + 1];

        ArrayList<BigInt> primeNumbers = new ArrayList<>();

        for (int i = 2; i <= squareRootOfNumber; i++) {
            if (!isNotPrime[i]) {
                for (int j = i * i; j <= squareRootOfNumber; j += i) {
                    isNotPrime[j] = true;
                }
            }
        }

        for (int i = 2; i <= squareRootOfNumber; i++) {
            if (!isNotPrime[i]) {
                primeNumbers.add(new BigInt(i));
            }
        }

        return primeNumbers;
    }

    public BigInt add(BigInt secondNumber) { // Метод, выполняющий суммирование двух BigInt типов.
        StringBuilder strBuilder = new StringBuilder();

        if (sign && !secondNumber.sign) {
            sign = false;
            BigInt result = new BigInt(secondNumber.subtract(this).toString());
            sign = true;
            return result;
        }

        if (!sign && secondNumber.sign) {
            secondNumber.sign = false;
            BigInt result = new BigInt(this.subtract(secondNumber).toString());
            secondNumber.sign = true;
            return result;
        }

        if (sign) {
            simpleAdder(secondNumber, strBuilder);

            strBuilder.append('-');

            return fromString(strBuilder.reverse().toString());
        }

        simpleAdder(secondNumber, strBuilder);

        return BigInt.fromString(strBuilder.reverse().toString());
    }

    private void simpleAdder(BigInt secondNumber, StringBuilder strBuilder) {
        int carrier = 0;
        for (int i = 0; i < Math.max(length(), secondNumber.length()); i++) {
            byte firstNumberDigit = i < length() ? dgt[i] : 0;
            byte secondNumberDigit = i < secondNumber.length() ? secondNumber.dgt[i] : 0;
            byte digitSum = (byte) (firstNumberDigit + secondNumberDigit + carrier);
            strBuilder.append(digitSum % 10);
            carrier = digitSum / 10;
        }

        if (carrier > 0) {
            strBuilder.append(carrier);
        }
    }

    public BigInt subtract(BigInt secondNumber) { // Метод, выполняющий вычитание двух BigInt типов.
        StringBuilder strBuilder = new StringBuilder();

        if (!sign && !secondNumber.sign) {
            if (!compareArray(dgt, secondNumber.dgt)) {
                String result = '-' + secondNumber.subtract(this).toString();
                return fromString(result);
            } else {
                return simpleSubtract(secondNumber, strBuilder);
            }
        }

        if (sign && !secondNumber.sign) {
            simpleAdder(secondNumber, strBuilder);

            strBuilder.append('-');

            return fromString(strBuilder.reverse().toString());
        }

        if (sign) {
            if (compareArray(dgt, secondNumber.dgt)) {
                String a = '-' + simpleSubtract(secondNumber, strBuilder).toString();
                return fromString(a);
            } else {
                return secondNumber.simpleSubtract(this, strBuilder);
            }
        }

        simpleAdder(secondNumber, strBuilder);

        return fromString(strBuilder.reverse().toString());
    }

    private BigInt simpleSubtract(BigInt secondNumber, StringBuilder strBuilder) { // Нужен для работы метода subtract.
        BigInt tempFirstNumber = new BigInt(this.toString());

        for (int i = 0; i < Math.max(tempFirstNumber.length(), secondNumber.length()); i++) {
            byte firstNumberDigit = i < tempFirstNumber.length() ? tempFirstNumber.dgt[i] : 0;
            byte secondNumberDigit = i < secondNumber.length() ? secondNumber.dgt[i] : 0;

            byte digitSubtract;

            if (firstNumberDigit >= secondNumberDigit) {
                digitSubtract = (byte) (firstNumberDigit - secondNumberDigit);
                strBuilder.append(digitSubtract);
            }

            if (firstNumberDigit < secondNumberDigit) {
                firstNumberDigit += 10;
                tempFirstNumber.dgt[i + 1]--;
                digitSubtract = (byte) (firstNumberDigit - secondNumberDigit);
                strBuilder.append(digitSubtract);
            }
        }

        return fromString(strBuilder.reverse().toString());
    }

    public BigInt multiply(BigInt secondNumber) { // Метод, выполняющий умножение двух BigInt типов.
        StringBuilder firstNumberStr = new StringBuilder();
        StringBuilder secondNumberStr = new StringBuilder();

        if (sign) {
            firstNumberStr.append(this.toStringWithoutDash());
        } else {
            firstNumberStr.append(this.toString());
        }

        if (secondNumber.sign) {
            secondNumberStr.append(secondNumber.toStringWithoutDash());
        } else {
            secondNumberStr.append(secondNumber.toString());
        }

        int firstNumberLength = firstNumberStr.length();
        int secondNumberLength = secondNumberStr.length();

        int[] result = new int[firstNumberLength + secondNumberLength];

        int firstNumberIndex = 0;
        int secondNumberIndex;

        for (int digit1 = firstNumberLength - 1; digit1 >= 0; digit1--) {
            int carry = 0;
            int firstNumberDigit = firstNumberStr.charAt(digit1) - '0';

            secondNumberIndex = 0;

            for (int digit2 = secondNumberLength - 1; digit2 >= 0; digit2--) {
                int secondNumberDigit = secondNumberStr.charAt(digit2) - '0';

                int sum = (firstNumberDigit * secondNumberDigit) + result[firstNumberIndex + secondNumberIndex] + carry;

                carry = sum / 10;

                result[firstNumberIndex + secondNumberIndex] = sum % 10;

                secondNumberIndex++;
            }

            if (carry > 0) {
                result[firstNumberIndex + secondNumberIndex] += carry;
            }

            firstNumberIndex++;
        }

        int i = result.length - 1;
        while (i >= 0 && result[i] == 0) {
            i--;
        }

        if (i == -1) {
            return ZERO;
        }

        StringBuilder resultString = new StringBuilder();
        if ((sign && !secondNumber.sign) || !sign && secondNumber.sign) {
            resultString.append('-');
        }

        while (i >= 0) {
            resultString.append(result[i]);
            i--;
        }

        return fromString(resultString.toString());
    }

    public BigInt divide(BigInt secondNumber) { // Метод, выполняющий деление двух BigInt типов.
        String result;

        if (sign && !secondNumber.sign) {
            BigInt positiveFirstNumber = new BigInt(this.toStringWithoutDash());
            if (secondNumber.isGreaterThan(positiveFirstNumber)) {
                return BigInt.ZERO;
            }

            result = '-' + positiveFirstNumber.simpleDivide(secondNumber).toString();
            return fromString(result);
        }

        if (!sign && secondNumber.sign) {
            BigInt positiveSecondNumber = new BigInt(secondNumber.toStringWithoutDash());
            if (positiveSecondNumber.isGreaterThan(this)) {
                return BigInt.ZERO;
            }

            result = '-' + this.simpleDivide(positiveSecondNumber).toString();
            return fromString(result);
        }

        if (sign) {
            BigInt positiveFirstNumber = new BigInt(this.toStringWithoutDash());
            BigInt positiveSecondNumber = new BigInt(secondNumber.toStringWithoutDash());
            if (positiveSecondNumber.isGreaterThan(positiveFirstNumber)) {
                return BigInt.ZERO;
            }

            result = positiveFirstNumber.simpleDivide(positiveSecondNumber).toString();
            return fromString(result);
        }

        return this.simpleDivide(secondNumber);
    }

    public int compareTo(BigInt secondNumber) {

        if (toString().equals(secondNumber.toString())) {
            return 0;

        } else {

            if (isGreaterThan(secondNumber)) {
                return 1;
            } else {
                return -1;
            }

        }

    }

    private BigInt simpleDivide(BigInt secondNumber) {

        if (secondNumber.equals(ZERO)) {
            throw new ArithmeticException(" Second operand is 0");
        }

        if (secondNumber.isGreaterThan(this)) {
            return ZERO;
        }

        String firstNumberStr = this.toString();
        String secondNumberStr = secondNumber.toString();
        StringBuilder secondNumberStrBuilder = new StringBuilder(secondNumber.toString());
        StringBuilder portionStr;

        int index;

        if (firstNumberStr.substring(0, secondNumber.length()).compareTo(secondNumberStr) >= 0) {
            portionStr = new StringBuilder(firstNumberStr.substring(0, secondNumber.length()));
            index = secondNumber.length() - 1;
        } else {
            portionStr = new StringBuilder(firstNumberStr.substring(0, secondNumber.length() + 1));
            index = secondNumber.length();
        }

        BigInt portionBigNumber = new BigInt(portionStr.toString());

        StringBuilder result = new StringBuilder();

        while (index < firstNumberStr.length()) {
            int i = 1;
            BigInt multiplier;
            while (i <= 9) {
                multiplier = new BigInt(i);
                if (secondNumber.multiply(multiplier).isGreaterThan(portionBigNumber)) {
                    result.append(--i);
                    break;
                }
                i++;
            }

            if (i == 10) {
                multiplier = new BigInt(--i);
                result.append(i);
            } else {
                multiplier = new BigInt(i);
            }

            portionStr.delete(0, index + 1);
            portionStr.append((portionBigNumber.subtract(secondNumber.multiply(multiplier))).toString());

            if (index < firstNumberStr.length() - 1) {
                portionStr.append(firstNumberStr.charAt(index + 1));
                index++;
            } else {
                index++;
            }

            while (compareStringBuilder(portionStr, secondNumberStrBuilder) < 0 && index < firstNumberStr.length() - 1) {
                portionStr.append(firstNumberStr.charAt(index + 1));
                result.append(0);
                index++;
            }

            portionBigNumber = new BigInt(portionStr.toString());
        }

        return fromString(result.toString());
    }

    public BigInt mod(BigInt secondNumber) {
        return this.subtract(this.divide(secondNumber).multiply(secondNumber));
    }

    public boolean isEven() {
        return this.mod(TWO).equals(ZERO);
    }

}
