package com.eazybytes.cards.mapper;

import com.eazybytes.cards.command.event.CardUpdatedEvent;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto) {
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setActiveSw(cards.isActiveSw());
        return cardsDto;
    }

    public static Cards mapToCards(CardUpdatedEvent cardUpdatedEvent, Cards cards) {
        cards.setCardType(cardUpdatedEvent.getCardType());
        cards.setTotalLimit(cardUpdatedEvent.getTotalLimit());
        cards.setAvailableAmount(cardUpdatedEvent.getAvailableAmount());
        cards.setAmountUsed(cardUpdatedEvent.getAmountUsed());
        return cards;
    }

}
