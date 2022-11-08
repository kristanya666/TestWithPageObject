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
        int initialSecondCardBalance = dashboardPage.getSecondCardBalance();  //изначальный баланс второй карты

        dashboardPage.selectCardToTransferMoney(1);  //нажать на кнопку "Пополнить" первой карты

        TransferMoneyPage transferMoneyPage = new TransferMoneyPage();
        transferMoneyPage.transferMoneyBetweenCards(2, "1000"); //пополнить первую карту на 1000 р. из счета второй карты

        //проверка балансов
        int expected1 = initialFirstCardBalance + 1000;
        var actual1 = dashboardPage.getFirstCardBalance();

        int expected2 = initialSecondCardBalance - 1000;
        var actual2 = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }
}
