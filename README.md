# ANCOL

Just toying with a LibGDX implementation of colonization.

## TODO List

 * Colonies
   * Break colony UI down into smaller ui components (started working on this)
   * ~~Workplaces should be drag and drop targets, colonists should be sources~~ (partially done, still needs some work)
   * Warehouses should have a limit
   * Warehouse slots should retain their wares
   * Implement food mechanics; colonists should starve if not enough food is available
   * Surroundings map (draw using real tiles)
   * Merge corn and fish wares into a single food type in warehouse view
   * UI for ships/wagon trains in colony
     * Repairing ships
 * Path finding for ships should allow them to enter land tiles with friendly colonies
 * Path finding for land units should allow them to enter sea tiles with friendly ships with empty cargo bays on them
 * Minimap
   * display
   * make it interactive (pan, zoom?)
 * Tile transitions
 * Game specific tiles and transitions
 * Custom UI for Android
 * Tilerenderer squishing on resize
 * Sidebar positioning on resize
 * Europe screen
 
## Done

 * ~~UI squishing and broken click targets on resize~~
 * ~~Texture packing as gradle task~~
 * ~~Hide transient goods like crosses or liberty bells from the warehouse view~~
 * ~~Render wares as icons~~
 * ~~Nations and unit's allegiance~~
 * Colonies
   * ~~Assign jobs to colonists (partially done; can assign job on tiles)~~

 


# Packing Textures

``./gradlew core:pack``