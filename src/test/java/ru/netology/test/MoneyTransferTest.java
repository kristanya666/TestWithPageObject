package ru.netology.test;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class MoneyTransferTest {
    DashboardPage dashboardPage = new DashboardPage();

    int initialAmountFirstCard = dashboardPage.getFirstCardBalance(); //изначальный баланс первой карты
    int initialAmountSecondCard = dashboardPage.getSecondCardBalance();  //изначальный баланс второй карты

    @BeforeAll
    public static void Authorization() {
        //вход в личный кабинет с вводом кода верификации
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);


    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {


        //нажать на кнопку - пополнить
        var cards = $$("li.list__item");
        var cardFirst= cards.get(0);
        cardFirst.$("[data-test-id=action-deposit").click();

        //пополнение карты
        $(withText("Пополнение карты")).shouldBe(Condition.visible);

        $("[data-test-id=amount] input").setValue("1000");
        $("[data-test-id=from] input").setValue("5559 0000 0000 0002");
        $("[data-test-id=action-transfer]").click();

        //Переход в личный кабинет обратно
        $(withText("Ваши карты")).shouldBe(Condition.visible);

        //проверка баланса

        int expected = initialAmountFirstCard + 1000;
        var actual = dashboardPage.getFirstCardBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        DashboardPage dashboardPage = new DashboardPage();

        //нажать на кнопку - пополнить
        var cards = $$("li.list__item");
        var cardSecond= cards.get(1);
        cardSecond.$("[data-test-id=action-deposit").click();

        //пополнение карты
        $(withText("Пополнение карты")).shouldBe(Condition.visible);

        $("[data-test-id=amount] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=amount] input").sendKeys(BACK_SPACE);
        $("[data-test-id=amount] input").setValue("1000");
        $("[data-test-id=from] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=from] input").sendKeys(BACK_SPACE);
        $("[data-test-id=from] input").setValue("5559 0000 0000 0001");
        $("[data-test-id=action-transfer]").click();

        //Переход в личный кабинет обратно
        $(withText("Ваши карты")).shouldBe(Condition.visible);

        //проверка баланса

        int expected = initialAmountSecondCard + 1000;
        var actual = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldShowBalanceCard() {
        DashboardPage dashboardPage = new DashboardPage();

        var balance = dashboardPage.getFirstCardBalance();
        System.out.println(balance);
        var balanceTwo = dashboardPage.getSecondCardBalance();
        System.out.println(balanceTwo);


        //проверка баланса первой карты и второй
        Assertions.assertEquals(balance, balanceTwo);

    }
}
