import androidx.compose.runtime.collectAsState
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.tetrisgrid.lib.StandardPieces
import com.example.tetrisgrid.lib.TetrisPiece
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class TetrisPieceTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun defaultInitialisation_isFourByFour_empty_uneditable() {
        val grid = TetrisPiece()
        composeTestRule.setContent {
            var cells = grid.active.collectAsState()
            grid.ShowGrid(cells.value)
        }

        composeTestRule.onNodeWithTag("0:0").performClick() // click should have no effect
        composeTestRule.mainClock.advanceTimeByFrame()

        composeTestRule.onNodeWithTag("0:0").assertIsDisplayed() // top left
        composeTestRule.onNodeWithTag("3:3").assertIsDisplayed() // bottom right
        composeTestRule.onNodeWithTag("4:0").assertDoesNotExist()
        assertEquals("0:0 inactive", getContentDescriptionForTag("0:0"))
    }

    @Test
    fun canInitialiseGridAndTogglePieces() {
        val grid = TetrisPiece(numCells = 3, initialCells = StandardPieces.LShapeThreeByTwo, canModify = true)
        composeTestRule.setContent {
            var cells = grid.active.collectAsState()
            grid.ShowGrid(cells.value)
        }

        composeTestRule.onNodeWithTag("0:2").performClick() // clear top-right cell
        composeTestRule.onNodeWithTag("1:1").performClick() // activate center cell
        composeTestRule.mainClock.advanceTimeByFrame()

        // [][][]     [][]
        // []     --> [][]
        assertEquals("0:2 inactive", getContentDescriptionForTag("0:2"))
        assertEquals("1:1 active", getContentDescriptionForTag("1:1"))
        assertEquals(StandardPieces.SquareShapeTwoByTwo, grid.active.value)
    }

    private fun getContentDescriptionForTag(tag: String): String {
        return composeTestRule.onNodeWithTag(tag).fetchSemanticsNode().config[SemanticsProperties.ContentDescription].first()
    }
}