package ru.tenebrius.lab2;

import java.util.Scanner;

/**
 * Вариант № 4. Необходимо написать программу, выполняющую расчет оплаты за телефон. Расчет может выполняться по одному
 * из двух видов тарифов.
 * При расчете по первому тарифу:
 * 1) Если на разговоры по телефону за месяц было потрачено в сумме не более К минут, то выставляется фиксированная
 * сумма А рублей;
 * 2) Если на разговоры по телефону за месяц было потрачено в сумме более К минут, то к фиксированной сумме
 * прибавляется оплата каждой дополнительный минуты (В рублей за минуту).
 * При расчете по второму тарифу:
 * 1) Если на разговоры по телефону за месяц было потрачено в сумме не более К минут, то сумма оплаты вычисляется
 * по формуле C*t, где t — время разговоров в минутах; C — стоимость минуты разговора;
 * 2) Если на разговоры по телефону за месяц было потрачено в сумме более К минут, то сумма оплаты вычисляется
 * по формуле D*t, где t — время разговоров в минутах; D — стоимость минуты разговора.
 * Исходные данные, водимые пользователем: значения K, t, A, B, C, D.
 */
public class Main {

    public static Tariff readType(String text, String value) throws NumberFormatException {
        System.out.println(text);
        String resultString;
        if (value == null) {
            Scanner in = new Scanner(System.in);
            resultString = in.nextLine();
        } else {
            resultString = value;
        }
        int result = Integer.parseInt(resultString);
        if (result != 0 && result != 1)
            throw new NumberFormatException();
        return result == 0 ? Tariff.BASIC : Tariff.PRO;
    }

    public static double readNumber(String message, double baseValue, String value) throws NumberFormatException {
        System.out.println(message + ";\n" + (value == null ? ("Базовое значение: " + baseValue) : ("Вы ввели: " + value)));
        String resultString;
        if (value == null) {
            Scanner in = new Scanner(System.in);
            resultString = in.nextLine();
        } else {
            resultString = value;
        }
        if (resultString.strip().length() == 0)
            return baseValue;
        double result = Double.parseDouble(resultString);
        if (result < 0)
            throw new NumberFormatException();
        return result;
    }

    /**
     * Функция высчитывает стоимость тарифа по введенным данным.
     *
     * @param tariff_ Тарифный план. Допустимые значения: 0, 1.
     * @param K_ Количество минут в тарифе.
     * @param t_ Количество потраченных минут.
     * @param A_ (tariff_ == 0) Стоимость фиксированного плана в рублях.
     * @param B_ (tariff_ == 0) Стоимость дополнительной минуты.
     * @param C_ (tariff_ == 1) Стоимость одной минуты.
     * @param D_ (tariff_ == 1) Стоимость минуты, если тариф превышен.
     * @return Стоимость тарифа за месяц.
     */
    public static Double calculatePrice(String tariff_, String K_, String t_, String A_, String B_, String C_, String D_) {
        Tariff tariff;
        double K;
        double t;
        double A = 0;
        double B = 0;
        double C = 0;
        double D = 0;
        double result;
        try {
            tariff = readType("""
                    Выберите тарифный план.
                    Базовый тариф - код 0
                    Продвинутый тариф - код 1
                    """, tariff_);
        } catch (NumberFormatException exception) {
            System.out.println("Вы ввели недопустимое значение тарифа. Допустимые значения: 0, 1");
            return null;
        }
        try {
            K = readNumber("Количество минут в тарифе.", 100, K_);
            if (tariff.equals(Tariff.BASIC)) {
                A = readNumber("Стоимость фиксированного плана в рублях", 200, A_);
                B = readNumber("Стоимость дополнительной минуты", 1, B_);
            } else {
                C = readNumber("Стоимость одной минуты", 1, C_);
                D = readNumber("Стоимость минуты, если тариф превышен", 100, D_);
            }
            t = readNumber("Количество потраченных минут.", 1, t_);
        } catch (NumberFormatException exception) {
            System.out.println("Вы ввели недопустимое значение числа. На вход принимаются дробные числа, где дробная часть разделена точкой.");
            return null;
        }
        result = Tariff.calculate(tariff, K, t, A, B, C, D);
        System.out.println("Стоимость абонента за месяц:\n" + result);
        return result;
    }

    public static void main(String[] args) {
        calculatePrice(null, null, null, null, null, null, null);
    }

    enum Tariff {
        BASIC,
        PRO;

        public static double calculate(Tariff tariff, Double K, Double t, Double A, Double B, Double C, Double D) {
            if (tariff.equals(BASIC)) {
                if (t <= K)
                    return A;
                else
                    return A + (t - K) * B;
            } else {
                if (t <= K)
                    return C * t;
                else
                    // TODO: Специально сфабрикованная ошибка
                    return D * t + 1;
            }
        }
    }
}