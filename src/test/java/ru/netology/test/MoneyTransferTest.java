package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should transfer money from the second card to the first")
    void shouldTransferMoneyBetweenOwnCards() {
        DashboardPage dashboardPage = new DashboardPage();
        int initialFirstCardBalance = dashboardPage.getFirstCardBalance(); //изначальный баланс первой карты

        var cardFirst = dashboardPage.getCardFirst();
        dashboardPage.selectCardToTransferMoney(cardFirst);  //нажать на кнопку "Пополнить" первой карты

        TransferMoneyPage transferMoneyPage = new TransferMoneyPage();
        var cardNumberSecond = DataHelper.getCardNumberSecond();
        transferMoneyPage.transferMoneyFromSecondCard(cardNumberSecond, "1000");  //пополнить первую карту на 1000 р.

        //проверка баланса
        int expected = initialFirstCardBalance + 1000;
        var actual = dashboardPage.getFirstCardBalance();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should transfer money from the first card to the second")
    void shouldTransferMoneySecondTime() {
        DashboardPage dashboardPage = new DashboardPage();
        int initialSecondCardBalance = dashboardPage.getSecondCardBalance(); //изначальный баланс второй карты

        var cardSecond = dashboardPage.getCardSecond();
        dashboardPage.selectCardToTransferMoney(cardSecond);  //нажать на кнопку "Пополнить" второй карты

        TransferMoneyPage transferMoneyPage = new TransferMoneyPage();
        var cardNumberFirst = DataHelper.getCardNumberFirst();
        transferMoneyPage.transferMoneyFromFirstCard(cardNumberFirst, "1000");  //пополнить вторую карту на 1000 р.

        //проверка баланса
        int expected = initialSecondCardBalance + 1000;
        var actual = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(expected, actual);
    }
}
