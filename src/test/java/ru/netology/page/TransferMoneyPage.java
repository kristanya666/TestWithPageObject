package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;


public class TransferMoneyPage {

   SelenideElement pageName = $(withText("Пополнение карты"));
   SelenideElement amount = $("[data-test-id=amount] input");
   SelenideElement from = $("[data-test-id=from] input");
   SelenideElement transferButton = $("[data-test-id=action-transfer]");


    public TransferMoneyPage() {
        pageName.shouldBe(visible);
    }

    public DashboardPage transferMoneyBetweenCards(int fromCardId, String amountOfMoney) {
        amount.setValue(amountOfMoney);
        from.setValue(String.valueOf(DataHelper.getCardNumber(fromCardId)));
        transferButton.click();
        return new DashboardPage();
    }
}
