# ANCOL

Just toying with a LibGDX implementation of colonization.

## TODO List

 * Colonies
   * Assign jobs to colonists (partially done; can assign job on tiles)
   * Break colony UI down into smaller ui components (started working on this)
   * Implement food mechanics; colonists should starve if not enough food is available
   * Surroundings map (draw using real tiles)
   * Merge corn and fish wares into a single food type in warehouse view
   * UI for ships/wagon trains in colony
     * Repairing ships
 * Minimap
   * display
   * make it interactive (pan, zoom?)
 * Tile transitions
 * Game specific tiles and transitions
 * Custom UI for Android
 * Tilerenderer squishing on resize
 * Sidebar positioning on resize
 * Add Europe screen
 
## Done

 * ~~UI squishing and broken click targets on resize~~
 * ~~Texture packing as gradle task~~
 * ~~Hide transient goods like crosses or liberty bells from the warehouse view~~
 * ~~Render wares as icons~~
 


# Packing Textures

``./gradlew core:pack``