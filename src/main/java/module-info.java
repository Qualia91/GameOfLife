module com.nick.wood.game_of_life {
    requires java.desktop;
    requires com.nick.wood.rendering.Renderer2D;

    exports com.nick.wood.game_of_life;
    exports com.nick.wood.game_of_life.model;
    exports com.nick.wood.game_of_life.model.universe;
}