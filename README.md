# ANCOL

Just toying with a LibGDX implementation of colonization.

## TODO List

 * Colonies
   * Assign jobs to colonists
   * Break colony UI down into smaller ui components
   * Implement food mechanics; colonists should starve if not enough food is available
   * Surroundings map (draw using real tiles)
   * Hide transient goods like crosses or liberty bells from the warehouse view
   * Render wares as icons
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
 


# Packing Textures

``./gradlew core:pack``