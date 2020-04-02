import java.math.BigInteger

typealias Cards = List<String>
typealias Card = String

data class Player(
    val name: String,
    val bet: Int,
    val cards: Cards
) {

    fun bestHand(tableCards: Cards): Hand {
        val possiblePair = getPair(tableCards + cards)
        if (possiblePair == null) {
            return Hand(tableCards + cards)
        } else {
            return Hand(possiblePair, 1)
        }
    }
}


data class Hand(
    val cards: Cards,
    val handRank: Int = 0
) {

    val sortedCards = (cards).sortedBy { it.rank() }.reversed()
    val cardString = sortedCards.map {
        it.rank().toString().padStart(2, '0')
    }.joinToString("")

    val value: BigInteger
        get() {
            return cardString.toBigInteger()
        }

}

fun getPair(cards: Cards): Cards? {
    val potentialPairs = (2..14).map { rank -> cards.filter { it.rank() == rank } }
    val pair = potentialPairs.filter {
        it.size == 2
    }.sortedBy {
        it[0].rank()
    }.lastOrNull()

    if (pair == null) return null

    val remainingCards = cards.filter {
        it != pair[0] && it != pair[1]
    }.sortedBy { it.rank() }.reversed()
        .take(3)
    return pair + remainingCards
}

fun findWinners(players: List<Player>, cardsOnTable: Cards): List<Player> {
    val sortedPlayers = players.sortedBy { it.bet }
    val playersInTheGame = sortedPlayers.filter { it.bet == sortedPlayers.last().bet }
    val winners = playersInTheGame.sortedBy { it.bestHand(cardsOnTable).value }
    return winners.filter { it.bestHand(cardsOnTable).value == winners.last().bestHand(cardsOnTable).value }
}

fun Card.rank(): Int {

    return when (this.first()) {
        'A' -> 14
        '2' -> 2
        '3' -> 3
        '4' -> 4
        '5' -> 5
        '6' -> 6
        '7' -> 7
        '8' -> 8
        '9' -> 9
        'T' -> 10
        'J' -> 11
        'Q' -> 12
        'K' -> 13
        else -> 0
    }
}