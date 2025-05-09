package com.example.blackjack.view;

import com.example.blackjack.domain.Money;
import com.example.blackjack.domain.gamer.Player;

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

    public void createBetting(List<Player> players) {
        for (Player player : players) {
            while (true) {
                try {
                    System.out.printf("%s의 배팅 금액은?%n", player.getName());
                    String inputLine = scanner.nextLine();
                    int amount = Integer.parseInt(inputLine.trim());

                    if (amount < 0) {
                        throw new IllegalArgumentException("배팅 금액은 음수가 될 수 없습니다.");
                    }

                    player.betMoney(new Money(amount));
                    break;

                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력해주세요.");
                }
            }
        }
    }

    public boolean askDrawCard(Player player) {
        while (true) {
            System.out.printf("%s는 한 장의 카드를 더 받겠습니까? (예는 y, 아니오는 n)%n", player.getName());
            String inputLine = scanner.nextLine();

            if (inputLine.equals("y")) {
                return true;
            } else if (inputLine.equals("n")) {
                return false;
            } else {
                System.out.println("y 또는 n으로 입력해주세요.");
            }
        }
    }

}
