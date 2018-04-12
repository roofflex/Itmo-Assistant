import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ItmoAssistant extends TelegramLongPollingBot{
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi=new TelegramBotsApi();
        try{
            botsApi.registerBot(new ItmoAssistant());
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "ItmoAssistant_bot";
    }

    @Override
    public String getBotToken() {
        return "552654554:AAEM8tMYsHBpeD6OgdfFk8pO_S2ytxtraTo";
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onUpdateReceived(Update update) {
        Long targetChatId=update.getMessage().getChatId();

        InlineKeyboardMarkup quickstartmarkup=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> quickstartkeyboard=new ArrayList<>();
        List<InlineKeyboardButton> schedule=new ArrayList<>();
        schedule.add(new InlineKeyboardButton().setText("Расписание").setCallbackData("Schedule"));
        List<InlineKeyboardButton> documents=new ArrayList<>();              //         RENAME!
        documents.add(new InlineKeyboardButton().setText("Материалы").setCallbackData("Documents"));
        List<InlineKeyboardButton> notification=new ArrayList<>();
        notification.add(new InlineKeyboardButton().setText("Оповещение").setCallbackData("Notification"));
        quickstartkeyboard.add(schedule);
        quickstartkeyboard.add(documents);
        quickstartkeyboard.add(notification);
        quickstartmarkup.setKeyboard(quickstartkeyboard);

        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")){
            SendMessage initMessage=new SendMessage()
                    .setChatId(targetChatId)
                    .setText("Привет, я твой ассистент по Университету Итмо! Пожалуйста, напиши номер своей группы на латинице, например V3246.");
            try{
                execute(initMessage);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

        if (update.getMessage().getText().length()==5){
            SendMessage initcompleteMessage=new SendMessage()
                    .setChatId(targetChatId)
                    .setText("Спасибо за регистрацию!");
            SendMessage quickstartMessage=new SendMessage()
                    .setChatId(targetChatId)
                    .setText("Выбирай, что нужно ;)")
                    .setReplyMarkup(quickstartmarkup);
            try{
                execute(initcompleteMessage);
                execute(quickstartMessage);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }

        }
    }
}
