import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    val player1 = Player("Mike", 10, listOf("TC","2D"))
    val player2 = Player("Ali", 10, listOf("TC","2D"))
    val player3 = Player("player3", 12, listOf("TC","2D"))
    val player4 = Player("player4", 12, listOf("JC","2D"))

    @Test
    fun `find best hand - highest value no table cards`() {
        assertEquals(1002.toBigInteger(),player1.bestHand(emptyList()).value)
    }

    @Test
    fun `find best hand - highest value with table cards`() {
        assertEquals(13121002.toBigInteger(),player1.bestHand(listOf("KD","QH")).value)
    }

    @Test
    fun `getPair() should return null when passed a list of cards with no pairs`() {
        assertEquals(null,getPair(listOf("2D","7D","TC","3D","9S","8C","AD")))
    }

    @Test
    fun `getPair() should return the matching pairs when passed a list of cards with 1 pair`() {
        assertEquals(listOf("7H", "7D", "AD", "TC", "9S"),getPair(listOf("7H","7D","TC","3D","9S","8C","AD")))
    }

    @Test
    fun `getPair() should return the top matching pairs when passed a list of cards with 2 pairs`() {
        assertEquals(listOf("TC", "TD", "AD", "9S", "8C"),getPair(listOf("7H","7D","TC","TD","9S","8C","AD")))
    }



    @Test
    fun `findWinners`() {
        assertEquals(emptyList<Player>(),findWinners(emptyList(), emptyList()))
    }

    @Test
    fun `should return 1 player if passed 1 player`() {
        assertEquals(listOf(player1), findWinners(listOf(player1), emptyList()))
    }

    @Test
    fun `should return 1 player that have the highest bet and same hand`() {
        assertEquals(listOf(player3), findWinners(listOf(player1,player3), emptyList()))
    }

    @Test
    fun `should return 2 players that have the highest bet and same hand`() {
        assertEquals(listOf(player1,player2), findWinners(listOf(player1,player2), emptyList()))
    }

    @Test
    fun `should return 1 player that wins, same bet and winning hand - highest value`() {
        assertEquals(listOf(player4), findWinners(listOf(player3,player4), emptyList()))
    }


    @Test
    fun `should return 1 player that wins, same bet and winning hand - highest value plus 5 table cards`() {
        assertEquals(listOf(player4), findWinners(listOf(player3,player4), listOf("TC","3D","9S","8C","AD")))
    }

    @Test
    fun `should return the player with winning hand - pair v no pair`() {

        assertEquals(listOf(player1),findWinners(listOf(player4,player1), listOf("TC","3D","9S","8C","AD")))
    }
}