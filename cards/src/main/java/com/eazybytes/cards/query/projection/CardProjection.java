package com.eazybytes.cards.query.projection;

import com.eazybytes.cards.command.event.CardCreatedEvent;
import com.eazybytes.cards.command.event.CardDeletedEvent;
import com.eazybytes.cards.command.event.CardUpdatedEvent;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("card-group")
public class CardProjection {

    private final ICardsService iCardsService;

    @EventHandler
    public void on(CardCreatedEvent cardCreatedEvent) {
        Cards card = new Cards();
        BeanUtils.copyProperties(cardCreatedEvent, card);
        iCardsService.createCard(card);
    }

    @EventHandler
    public void on(CardUpdatedEvent cardUpdatedEvent) {
        iCardsService.updateCard(cardUpdatedEvent);
    }

    @EventHandler
    public void on(CardDeletedEvent cardDeletedEvent) {
        iCardsService.deleteCard(cardDeletedEvent.getCardNumber());
    }
}
