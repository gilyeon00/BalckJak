package com.example.blackjack.view;

import com.example.blackjack.Money;
import com.example.blackjack.Player;

import java.util.*;

public class ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * 사용자에게 이름을 입력받아 Player 리스트를 생성한다. (1~6명)
     * 이름은 쉼표(,)로 구분되며, 중복 또는 공백 이름은 허용되지 않는다.
     */
    public List<Player> readPlayers() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요. (쉼표 기준으로 분리, 1~6명)");

        String input = scanner.nextLine();
        String[] names = input.split(",");

        if (names.length < 1 || names.length > 6) {
            throw new IllegalArgumentException("플레이어 수는 1명 이상 6명 이하만 가능합니다.");
        }

        Set<String> uniqueNames = new HashSet<>();
        List<Player> players = new ArrayList<>();

        for (String name : names) {
            String trimmedName = name.trim();

            if (trimmedName.isEmpty()) {
                throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
            }

            if (!uniqueNames.add(trimmedName)) {
                throw new IllegalArgumentException("중복된 이름은 사용할 수 없습니다: " + trimmedName);
            }

            players.add(new Player(trimmedName));
        }

        return players;
    }
}
