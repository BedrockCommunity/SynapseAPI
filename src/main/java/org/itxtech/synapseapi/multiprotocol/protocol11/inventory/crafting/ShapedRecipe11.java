package org.itxtech.synapseapi.multiprotocol.protocol11.inventory.crafting;

import cn.nukkit.Server;
import cn.nukkit.inventory.CraftingManager;
import cn.nukkit.inventory.CraftingRecipe;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author CreeperFace
 */
public class ShapedRecipe11 implements CraftingRecipe {

    private Item output;

    private UUID uuid = null;

    private final Map<Character, String> shapes = new HashMap<>();

    private final Map<Integer, Map<Integer, Item>> ingredients = new HashMap<>();

    private final Map<Character, List<Entry>> shapeItems = new HashMap<>();

    public ShapedRecipe11(Item result, int height, int width) {
        for (int y = 0; y < height; y++) {
            if (width == 0 || width > 3) {
                throw new IllegalStateException("Crafting rows should be 1, 2, 3 characters, not " + width);
            }

            this.ingredients.put(y, new HashMap<Integer, Item>() {
                {
                    for (int i = 0; i < width; i++) {
                        put(i, null);
                    }
                }
            });
        }

        this.output = result.clone();
    }

    @Override
    public boolean matchItems(Item[][] items, Item[][] items1) {
        return false;
    }

    @Override
    public List<Item> getExtraResults() {
        return null;
    }

    public int getWidth() {
        return this.ingredients.get(0).size();
    }

    public int getHeight() {
        return this.ingredients.size();
    }

    @Override
    public Item getResult() {
        return this.output;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID id) {
        if (this.uuid != null) {
            throw new IllegalStateException("Id is already set");
        }
        this.uuid = id;
    }

    public ShapedRecipe11 addIngredient(int x, int y, Item item) {
        this.ingredients.get(y).put(x, item.clone());
        return this;
    }

    public ShapedRecipe11 setIngredient(String key, Item item) {
        return this.setIngredient(key.charAt(0), item);
    }

    public ShapedRecipe11 setIngredient(char key, Item item) {
        if (!this.shapes.containsKey(key)) {
            throw new RuntimeException("Symbol does not appear in the shape: " + key);
        }

        this.fixRecipe(key, item);

        return this;
    }

    protected void fixRecipe(char key, Item item) {
        for (Entry entry : this.shapeItems.get(key)) {
            this.ingredients.get(entry.y).put(entry.x, item.clone());
        }
    }

    public Map<Integer, Map<Integer, Item>> getIngredientMap() {
        Map<Integer, Map<Integer, Item>> ingredients = new HashMap<>();
        for (int y : this.ingredients.keySet()) {
            Map<Integer, Item> row = this.ingredients.get(y);

            ingredients.put(y, new HashMap<>());

            for (int x : row.keySet()) {
                Item ingredient = row.get(x);

                if (ingredient != null) {
                    ingredients.get(y).put(x, ingredient.clone());
                } else {
                    ingredients.get(y).put(x, Item.get(Item.AIR));
                }
            }

        }

        return ingredients;
    }

    public Item getIngredient(int x, int y) {
        if (this.ingredients.containsKey(y)) {
            if (this.ingredients.get(y).containsKey(x)) {
                return this.ingredients.get(y).get(x) != null ? this.ingredients.get(y).get(x) : Item.get(Item.AIR);
            }
        }

        return Item.get(Item.AIR);
    }

    public Map<Character, String> getShape() {
        return shapes;
    }

    public void registerToCraftingManager(CraftingManager manager) {
        //manager.registerShapedRecipe(this);
    }

    public boolean requiresCraftingTable() {
        return this.getHeight() > 2 || this.getWidth() > 2;
    }

    @Override
    public List<Item> getAllResults() {
        return null;
    }

    public static class Entry {
        public final int x;
        public final int y;

        public Entry(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}