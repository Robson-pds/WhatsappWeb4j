package it.auties.whatsapp;

import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.api.WhatsappOptions.MobileOptions;
import it.auties.whatsapp.model.mobile.VerificationCodeMethod;
import it.auties.whatsapp.model.mobile.VerificationCodeResponse;
import it.auties.whatsapp.util.JacksonProvider;

import java.util.Scanner;

public class MobileTest implements JacksonProvider {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        var options = MobileOptions.builder()
                .phoneNumber("+16137770646")
                .verificationCodeMethod(VerificationCodeMethod.SMS)
                .verificationCodeHandler(MobileTest::onScanCode)
                .build();
        var whatsapp = Whatsapp.lastConnection(options)
                .addLoggedInListener(() -> System.out.println("Connected"))
                .addNewMessageListener(message -> System.out.println(message.toJson()))
                .addContactsListener((api, contacts) -> System.out.printf("Contacts: %s%n", contacts.size()))
                .addChatsListener(chats -> System.out.printf("Chats: %s%n", chats.size()))
                .addNodeReceivedListener(incoming -> System.out.printf("Received node %s%n", incoming))
                .addNodeSentListener(outgoing -> System.out.printf("Sent node %s%n", outgoing))
                .addActionListener((action, info) -> System.out.printf("New action: %s, info: %s%n", action, info))
                .addSettingListener(setting -> System.out.printf("New setting: %s%n", setting))
                .addContactPresenceListener((chat, contact, status) -> System.out.printf("Status of %s changed in %s to %s%n", contact.name(), chat.name(), status.name()))
                .addAnyMessageStatusListener((chat, contact, info, status) -> System.out.printf("Message %s in chat %s now has status %s for %s %n", info.id(), info.chatName(), status, contact == null ? null : contact.name()))
                .addDisconnectedListener(reason -> System.out.printf("Disconnected: %s%n", reason));
        whatsapp.connect().join();
    }

    private static String onScanCode(VerificationCodeResponse type) {
        System.out.println("Enter OTP: ");
        var scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
}