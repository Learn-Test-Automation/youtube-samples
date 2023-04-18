package com.github.wasiqb.yt.appium.w3c;

import static java.time.Duration.ZERO;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

@AllArgsConstructor
public class FingerGestureUtils<D extends AppiumDriver> {
    @Getter
    @AllArgsConstructor
    public enum Direction {
        LEFT (-1, 0),
        RIGHT (1, 0),
        UP (0, -1),
        DOWN (0, 1);

        private final int x;
        private final int y;
    }

    private final D driver;

    public Sequence swipe (final Direction direction, final int distance) {
        return swipe (direction, null, distance);
    }

    public Sequence swipe (final Direction direction, final WebElement element, final int distance) {
        final var start = getSwipeStartPosition (element);
        final var end = getSwipeEndPosition (direction, element, distance);

        final var finger = new PointerInput (TOUCH, "Finger1");
        final var sequence = new Sequence (finger, 0);

        sequence.addAction (finger.createPointerMove (ZERO, viewport (), start.getX (), start.getY ()));
        sequence.addAction (finger.createPointerDown (LEFT.asArg ()));
        sequence.addAction (finger.createPointerMove (Duration.ofMillis (300), viewport (), end.getX (), end.getY ()));
        sequence.addAction (finger.createPointerUp (LEFT.asArg ()));

        return sequence;
    }

    public Sequence tap (final WebElement element) {
        final var start = getElementCenter (element);

        final var finger = new PointerInput (TOUCH, "Finger1");
        final var sequence = new Sequence (finger, 0);

        sequence.addAction (finger.createPointerMove (ZERO, viewport (), start.getX (), start.getY ()));
        sequence.addAction (finger.createPointerDown (LEFT.asArg ()));
        sequence.addAction (finger.createPointerUp (LEFT.asArg ()));

        return sequence;
    }

    private Point getCorrectedCoordinates (final Point point) {
        final var screenSize = getScreenSize ();
        var x = point.getX ();
        var y = point.getY ();

        if (x >= screenSize.getWidth ()) {
            x = screenSize.getWidth () - 5;
        }
        if (y >= screenSize.getHeight ()) {
            y = screenSize.getHeight () - 5;
        }
        if (x < 0) {
            x = 5;
        }
        if (y < 0) {
            y = 5;
        }

        return new Point (x, y);
    }

    private Point getElementCenter (final WebElement element) {
        final var location = element.getLocation ();
        final var size = element.getSize ();
        final var x = (size.getWidth () / 2) + location.getX ();
        final var y = (size.getHeight () / 2) + location.getY ();
        return getCorrectedCoordinates (new Point (x, y));
    }

    private Dimension getScreenSize () {
        return this.driver.manage ()
            .window ()
            .getSize ();
    }

    private Point getSwipeEndPosition (final Direction direction, final WebElement element, final int distance) {
        final var start = getSwipeStartPosition (element);
        final var x = start.getX () + ((start.getX () * direction.getX () * distance) / 100);
        final var y = start.getY () + ((start.getY () * direction.getY () * distance) / 100);
        return getCorrectedCoordinates (new Point (x, y));
    }

    private Point getSwipeStartPosition (final WebElement element) {
        final var screenSize = getScreenSize ();
        var x = screenSize.getWidth () / 2;
        var y = screenSize.getHeight () / 2;
        if (element != null) {
            final var point = getElementCenter (element);
            x = point.getX ();
            y = point.getY ();
        }
        return new Point (x, y);
    }
}
