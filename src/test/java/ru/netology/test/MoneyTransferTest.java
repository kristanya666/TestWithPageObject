package ru.netology.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferMoneyPage;
import static com.codeborne.selenide.Selenide.*;


public class MoneyTransferTest {

    @BeforeAll
    public static void authorization() {
        //вход в личный кабинет с вводом кода верификации
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);


    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        DashboardPage dashboardPage = new DashboardPage();

        int initialAmountFirstCard = dashboardPage.getFirstCardBalance(); //изначальный баланс первой карты
        int initialAmountSecondCard = dashboardPage.getSecondCardBalance();  //изначальный баланс второй карты

        var cardFirst = dashboardPage.getCardFirst();
        dashboardPage.selectCardToTransferMoney(cardFirst);

        TransferMoneyPage transferMoneyPage = new TransferMoneyPage();
        var cardNumberSecond = DataHelper.getCardNumberSecond();
        transferMoneyPage.transferMoneyFromSecondCard(cardNumberSecond, "1000");

        //проверка баланса
        int expected = initialAmountFirstCard - 1000;
        var actual = initialAmountSecondCard + 1000;
        Assertions.assertEquals(expected, actual);
    }
}
