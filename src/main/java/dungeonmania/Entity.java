package dungeonmania;

import java.util.UUID;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * Entity class.
 */

public abstract class Entity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;
    
    /**
     * Constructor for Entity.
     * @param position
     * @param type
     * @param isInteractable
     */
    public Entity(Position position, String type, boolean isInteractable) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

    /**
     * Gets the id.
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the type.
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the position.
     * @return Position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the x value of position.
     * @return Int
     */
    public int getX() {
        return position.getX();
    }

    /**
     * Gets the y value of position.
     * @return Int
     */
    public int getY() {
        return position.getY();
    }

    /**
     * Sets the position.
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Checks if entity is interactable.
     * @return Boolean
     */
    public boolean isInteractable() {
        return isInteractable;
    }

    /**
     * Generates an EntityResponse.
     * @return EntityResponse
     */
    public EntityResponse getEntityResponse() {
        return new EntityResponse(this.id, this.type, this.position, this.isInteractable);
    }

    /**
     * Interacts with entity.
     * @param game
     * @param entity
     */
    public abstract void interact(Game game, Entity entity);


    /**
     * Ticks the entity.
     * @param game
     */
    public abstract void tick(Game game);
}
