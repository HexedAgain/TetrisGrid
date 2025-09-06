# Tetris Piece Tools

In the TDD workshop [^1] we started working on a tetris game.
The set of pieces in the standard game is pretty small, but it would be nice to have a standalone tool where we can
- view those pieces (and see how they look when you rotate them),
- create new pieces
- edit existing pieces
- delete pieces
- enable / disable pieces
- control the likelihood of any piece being the next piece to fall
- label them and sort them (either by name, or by number of blocks, weighting, "difficulty", or any other useful data point).
- save these pieces so that they are available when we next load the app

With such a tool we can customise our game, create our own difficulty levels [^2], or possibly share these customisations with other users and such.

There is no need to integrate this into a real Tetris game.

### What is in this repo
The libs package contains the tools you will need to implement the features for this project, it contains
- functionality to render tetris pieces (though there are also some vector resources just to get us started).
- functionality to create, transform, and randomly generate pieces - amongst other things

Gradle dependencies for DI (koin), and serialization (kotlinx), should you need them, have been rigged up and a basic koin module is in place

### A rough roadmap
1. We should first be able to render a column of the basic pieces on some listing page, Each item should include a graphic and a title for the piece
2. When we click on a piece, this should show the details screen
    1. The details screen should show the piece (preferably with more prominence than in the list view, but that's a UI detail)
    2. It should also show the title
3. From the listing screen, there should be some UI element (be it a FAB or otherwise) which when clicked will bring up a create screen
    1. If not already done so by this point, you will need to integrate the `TetrisGrid` class, we should start with an empty grid
    2. We should be able to click cells on this grid to mark which tiles contribute to our new piece
    3. We should have somewhere to say what our piece will be called
    4. We need a button to add this piece to our collection of pieces
    5. Are there any checks we should perform to determine whether the piece is valid before adding it? i.e.
        - what if the piece is the same as another, albeit rotated or moved in some way?
        - what if this piece is just a collection of "islands" that are not connected?
        - ???
    6. We should show invalidness in some way on the UI and disable saving until remedied.
    7. If you rotate the screen do the active cells remain active?
4. We should be able to edit a piece, this can be invoked either from the listing screen (UI on each item), on the details screen, or both
5. We should be able to delete a piece, this should be invoked from the listing screen.
6. Now that we can Create, Read, Update, and Delete pieces (aka we have a barebones CRUD app), let's flesh things out a little further.
    - **Details screen**:
        1. Provide a means to rotate a piece (CW and CCW)
        2. Show the piece weighting (when implemented on the creation / edit screen)
        3. Can you think of any other information that would be nice to have here?
   - **Listing screen**:
       1. For each piece we should have some UI via which to mark it as enabled or disabled (whether disabled or not should be shown on the details screen)
       2. We should have some UI via which to show / hide disabled pieces
       3. Provide some UI to sort the pieces (by name, number of pieces, weight, or any other data point you added)
       4. Deleting a piece without verifying could be a poor UX (fat fingers), provide some means to check if that was what the user intended
   - **Creation / Edit screen**:
       1. When creating a new piece it would be nice to randomly generate a piece to get us started, so that we can tweak it
       2. We should be able to assign a weight to this piece
           1. A value of 1 (default value) would indicate it has the same likelihood of being selected as any other, whereas a higher value would make it more likely to get selected, and lower means less likely
           2. We should prevent user from entering negative or zero values
           3. We should provide some way to visualise the impact of changing these weights [^3]
8. Finally we should be able to save our pieces so that when we next load the app we don't start from scratch again [^4]

## Notes:
- A polished UI isn't really important! so long as it is functional, then it's all good - that time can be put to better use
- Try to imagine others will be following and extending upon your work; you will want to leave them a clean and consistent architecture
- There are some unit tests covering most of the library tools you'll need to integrate (there is also an androidTest), use this for documentation if it helps

[^1]: That workshop needs to be revived at some point
[^2]: Maybe we create a 1x1 piece and make it very likely to fall next for those who want "easy mode"!
[^3]: We could render a test grid for the user to randomly generate the next piece, or we could show the probability it would be selected, or do something else, the choice here is yours
[^4]: It would be extra nice if user could save this data to a file of their own choosing, and be presented with options for which file to load if they want to import pieces (maybe user wants an easy set, and a challenging set) 
