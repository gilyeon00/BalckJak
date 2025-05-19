package com.example.blackjack.dto;

import com.example.blackjack.domain.gamer.Player;

import java.util.List;

public record PlayerPartition(List<Player> survived, List<Player> busted) {}
