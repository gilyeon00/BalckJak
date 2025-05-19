package com.example.blackjack.domain.gamer;

import java.util.List;

public record PlayerPartition(List<Player> survived, List<Player> busted) {}
