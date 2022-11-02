package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    private SelenideElement cardFirst = $("div [data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement cardSecond = $("div [data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");

    public SelenideElement getCardFirst() {
        return cardFirst;
    }

    public SelenideElement getCardSecond() {
        return cardSecond;
    }


    public DashboardPage() {
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.get(1).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferMoneyPage selectCardToTransferMoney(SelenideElement cardNumber) {
        SelenideElement cardButton = cardNumber.$("[data-test-id=action-deposit");
        cardButton.click();
        return new TransferMoneyPage();
    }

}
