package com.eazybytes.cards.command.interceptor;

import com.eazybytes.cards.command.CreateCardCommand;
import com.eazybytes.cards.command.DeleteCardCommand;
import com.eazybytes.cards.command.UpdateCardCommand;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CardCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CardsRepository cardsRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (CreateCardCommand.class.equals(command.getPayloadType())) {
                CreateCardCommand createCardCommand = (CreateCardCommand) command.getPayload();
                cardsRepository.findByMobileNumberAndActiveSw(createCardCommand.getMobileNumber(), true)
                        .ifPresent(cards -> {
                            throw new CardAlreadyExistsException("Card already created with given mobileNumber: " + createCardCommand.getMobileNumber());
                        });
            } else if (UpdateCardCommand.class.equals(command.getPayloadType())) {
                UpdateCardCommand updateCardCommand = (UpdateCardCommand) command.getPayload();
                cardsRepository.findByMobileNumberAndActiveSw(updateCardCommand.getMobileNumber(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", updateCardCommand.getMobileNumber()));

            } else if (DeleteCardCommand.class.equals(command.getPayloadType())) {
                DeleteCardCommand deleteCardCommand = (DeleteCardCommand) command.getPayload();
                cardsRepository.findByCardNumberAndActiveSw(deleteCardCommand.getCardNumber(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", deleteCardCommand.getCardNumber().toString()));
            }
            return command;
        };
    }
}
